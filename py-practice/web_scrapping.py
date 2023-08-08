import requests
from bs4 import BeautifulSoup

headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}
data = requests.get("https://movie.daum.net/ranking/boxoffice/yearly", headers=headers)

soup = BeautifulSoup(data.text, 'html.parser')

# select를 이용해서 li들을 불러오기
# #mainContent > div > div.box_boxoffice > ol > li
movies = soup.select('.kakao_article > .section_ranking > .box_boxoffice > .list_movieranking > li')
for movie in movies:
    tag_element = movie.select_one('.tit_item > a')
    if not tag_element:
        continue
    print(tag_element.text)
