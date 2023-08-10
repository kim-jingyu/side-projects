import random
import requests
from bs4 import BeautifulSoup
from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.daummovie

# movies
def insert_all():
    # URL을 읽어서 HTML을 받아오기
    headers = {'User-Agent' : "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"}
    data = requests.get('https://movie.daum.net/ranking/boxoffice/yearly', headers=headers)

    soup = BeautifulSoup(data.text, 'html.parser')
    movies = soup.select('#mainContent > div > div.box_boxoffice > ol > li')

    for movie in movies:
        title_tag_element = movie.select_one('.tit_item > a')
        if not title_tag_element:
            continue
        title = title_tag_element.text

        open_date_tag_element = movie.select_one('.txt_info > .info_txt:nth-child(1) > span')
        if not open_date_tag_element:
            continue
        open_date = open_date_tag_element.text

        open_year, open_month, open_day = map(int, open_date.split('.'))
        open_year += 2000

        viewers_tag_element = movie.select_one('.txt_info > .info_txt:nth-child(2)')
        if not viewers_tag_element:
            continue
        viewers_tag_element.find('span').decompose()
        viewers = viewers_tag_element.text

        poster_image_tag_element = movie.select_one('.poster_movie > img')
        if not poster_image_tag_element:
            continue
        poster_image_url = poster_image_tag_element['src']
        if not poster_image_url:
            continue

        poster_info_tag_element = movie.select_one('.poster_info > a')
        if not poster_info_tag_element:
            continue
        poster_info_url = poster_info_tag_element['href']
        poster_info_url += 'https://movie.daum.net/' + poster_info_url

        find_movie = db.movies.find_one({'title': title})
        if find_movie:
            continue

        likes = random.randrange(0, 100)

        doc = {
            'title': title,
            'open_year': open_year,
            'open_month': open_month,
            'open_day': open_day,
            'viewers': viewers,
            'poster_url': poster_image_url,
            'info_url': poster_info_url,
            'likes': likes,
            'trashed': False
        }
        db.movies.insert_one(doc)
        print('저장 완료')


if __name__ == '__main__':
    db.movies.drop()

    insert_all()
