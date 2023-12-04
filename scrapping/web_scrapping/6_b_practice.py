import requests
from bs4 import BeautifulSoup

url = "https://comic.naver.com/index"
headers = {
    "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}

res = requests.get(url, headers)
res.raise_for_status()

with open("result.html", "w", encoding="utf-8") as f:
    f.write(res.text)

soup = BeautifulSoup(res.text, "lxml")

print(soup.title)
print(soup.title.get_text())
print(soup.a)  # soup 객체에서 처음 발견되는 a element
print(soup.a.attrs)  # a element의 속성 정보
print(soup.a["href"])  # a element의 href 속성 값 정보 출력

soup.find("a", attrs={"class":"Nbtn_upload"}) # 클래스 값 지정
soup.find(attrs={"class":"Nbtn_upload"})

rank = soup.find("li", attrs={"class": "rank"})
print(rank.a.get_text())
print(rank.next_sibling.next_sibling)
print(rank.previous_sibling.previous_sibling)
print(rank.parent)
print(rank.find_next_sibling("li"))
print(rank.find_previous_sibling("li"))
print(rank.find_next_siblings("li"))

webtoon = soup.find("a", text="독립일기-11화 밥공기 딜레마")