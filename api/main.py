from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse
import requests
from PIL import Image
import io
import os
from dotenv import load_dotenv
import requests

app = FastAPI()
load_dotenv()

@app.post("/api/v1/food/nutrition")
async def create_upload_file(file: UploadFile = File(...)):
    try:
        # 파일을 메모리에 저장
        contents = await file.read()

        # 이미지를 PIL로 열어 544x544로 리사이즈
        image = Image.open(io.BytesIO(contents))
        image = image.resize((544, 544))
        img_byte_arr = io.BytesIO()
        image.save(img_byte_arr, format='JPEG')  # Calorie Mama가 JPEG를 요구하는 경우
        img_byte_arr = img_byte_arr.getvalue()
    
        # 음식 정보를 추출하기 위한 Calorie Mama API
        base_url = "https://api-2445582032290.production.gw.apicast.io/v1/foodrecognition/full"
        food_api_key = os.getenv("FOOD_API_KEY")
        url = f"{base_url}?user_key={food_api_key}"

        files = {
            "file": ("filename.jpg", img_byte_arr, "image/jpeg")
        }
        
        response = requests.post(url, files=files)
        response_code = response.status_code
        food_title = ""
        food_nutrition = ""
        if response_code == 200:
            data = response.json()
            # AI 가 선정한 가장 유사도가 높은 음식으로 가져온다.
            highest_score_item = max(data["results"][0]["items"], key=lambda item: item["score"])
            food_title = highest_score_item["name"] 
            food_nutrition = highest_score_item["nutrition"]
        else:
            raise Exception(f"Calorie Mama API 요청 실패: status_code={response_code}")

        # 음식 이름을 추출하기 위한 Google Transition API
        google_api_key = os.getenv("GOOGLE_API_KEY")
        url =  f'https://translation.googleapis.com/language/translate/v2?q={food_title}&target=ko&key={google_api_key}'
        res = requests.get(url, verify=False)
        food_title = res.json()["data"]["translations"][0]["translatedText"]
        print(food_title)

        response = {
            "food_title" : food_title,
            "food_nutrition": food_nutrition
        }

        return response
    except Exception as e:
        return JSONResponse(status_code=500, content= {"error" : "Internal Server Error", "message" : str(e) })

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
