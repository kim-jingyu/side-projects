import requests

url = "https://nadocoding.tistory.com/"
headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}

res = requests.get(url, headers)
res.raise_for_status()

with open("result.html", "w", encoding="utf-8") as f:
    f.write(res.text)
