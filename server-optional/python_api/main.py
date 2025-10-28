from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title = "CyberAI Inference API")


class AnalyzeIn(BaseModel):
    text: str


class AnalyzeOut(BaseModel):
    label: str
    score: float


@app.get("/")
def root():
    return {"message": "CyberAI API running", "usage": "POST /analyze"}


@app.post("/analyze", response_model = AnalyzeOut)
def analyze(data: AnalyzeIn):
    t = data.text.lower()
    if "malware" in t or "phish" in t:
        return AnalyzeOut(label = "suspicious", score = 0.9)
    if "http://" in t or "https://" in t:
        return AnalyzeOut(label = "review", score = 0.55)
    return AnalyzeOut(label = "normal", score = 0.1)


# ✅ Allows running with: python main.py
if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host = "127.0.0.1", port = 8000, reload = True)
