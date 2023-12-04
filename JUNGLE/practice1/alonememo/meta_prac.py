import requests
from bs4 import BeautifulSoup

url = 'https://movie.daum.net/ranking/boxoffice/yearly'
headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}
data = requests.get(url, headers=headers)
soup = BeautifulSoup(data.text, 'html.parser')

og_title = soup.select_one('meta[property="og:title"]')
og_image = soup.select_one('meta[property="or:image"]')
og_description = soup.select_one('meta[property="og:description"]')

print(og_title['content'])