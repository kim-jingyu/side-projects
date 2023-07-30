import requests
from bs4 import BeautifulSoup

url = "https://www.megabox.co.kr/movie"
headers = {"User-Agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}

res = requests.get(url, headers=headers)
res.raise_for_status()

soup = BeautifulSoup(res.text, "lxml")

print(soup)

# cartoons = soup.find_all("p", attrs={"class": "tit"})
# for cartoon in cartoons:
#     print(cartoon.get_text())