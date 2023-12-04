import requests
from bs4 import BeautifulSoup

headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}
data = requests.get("https://movie.daum.net/ranking/boxoffice/yearly")

soup = BeautifulSoup(data.text, 'html.parser')

# 개봉일, 관객 수, 포스터 이미지 파일의 URL, 영화 세부 정보의 URL

movies = soup.select('#mainContent > div > div.box_boxoffice > ol > li')
for movie in movies:
    movie_name = movie.select_one('.tit_item > a').text
    open_day = movie.select_one('.info_txt > .txt_num').text
    audience_num = movie.select_one('.txt_info > .info_txt:nth-child(2)')
    audience_num.find('span').decompose()
    audience_num = audience_num.text
    poster_img_url = movie.select_one('.poster_movie > img')["src"]
    poster_info_url = movie.select_one('.poster_info > a')["href"]

    print("영화 제목: ", movie_name, ", 개봉일: ", open_day, ", 관객 수:", audience_num, ", 포스터 이미지 파일의 URL: ", poster_img_url, ", 영화 세부 정보의 URL: ", poster_info_url)
