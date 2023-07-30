import requests
from bs4 import BeautifulSoup

url = "https://www.megabox.co.kr/movie"

res = requests.get(url)
res.raise_for_status()

soup = BeautifulSoup(res.text, "lxml")
cartoons = soup.find_all("p", attrs={"class": "tit"})
for cartoon in cartoons:
    print(cartoon.get_text())