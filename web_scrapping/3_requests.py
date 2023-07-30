import requests

res = requests.get("https://www.google.com")
res.raise_for_status()

# print("응답코드:", res.status_code)
#
# if res.status_code == requests.codes.ok:    # 200
#     print("정상입니다.")
# else:
#     print("문제가 생겼습니다. 에러 코드:", res.status_code)

print(res.text)

with open("mygoogle.html", "w", encoding="utf-8") as f:
    f.write(res.text)
