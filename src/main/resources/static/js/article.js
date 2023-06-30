// 삭제 기능
const deleteButton = document.getElementById('delete-btn')

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value
        /*fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
        .then(() => {
            alert('삭제가 완료되었습니다.')
            location.replace('/articles')
        })*/
        function success() {
            alert("삭제가 완료되었습니다.")
            location.replace('/articles')
        }
        function fail() {
            alert('삭제에 실패했습니다.')
            location.replace("/articles")
        }

        httpRequest("DELETE", "/api/articles" + id, null, success, fail)
    })
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn')

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search)
        let id = params.get('id')

        /*fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => {
            alert('수정이 완료되었습니다.')
            location.replace(`/articles/${id}`)
        })*/

        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success() {
            alert('수정이 완료되었습니다.')
            location.replace("/articles/" + id)
        }
        function fail() {
            alert('수정에 실패했습니다.')
            location.replace("/articles/" + id)
        }

        httpRequest("PUT", "/api/articles" + id, body, success, fail)
    })
}

const createButton = document.getElementById('create-btn')

if (createButton) {
    createButton.addEventListener('click', event => {
        /*fetch('/api/articles',{
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => {
            alert('등록 완료되었습니다.')
            location.replace('/articles')
        })*/
        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success() {
            alert('등록이 완료되었습니다.')
            location.replace("/articles")
        }
        function fail() {
            alert('등록에 실패했습니다.')
            location.replace("/articles")
        }

        httpRequest("POST", "/api/articles", body, success, fail)
    })
}

// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null
    var cookie = document.cookie.split(";")

    cookie.some(function (item)){
        item = item.replace(" ", "")

        var dic = item.split("=")

        if (key === dic[0]) {
            result = dic[1]
            return true
        }
    }

    return result
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail){
    fetch(url, {
        method: method,
        headers: {
            // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가한다.
            Authorization: "Bearer " + localStorage.getItem("access_token"),
            "Content-Type": "application/json"
        },
        body: body
    }).then((response) => {
        if (response.status === 200 || response.status == 201){
            return success()
        }
        const refresh_token = getCookie("refresh_token")
        // access_token 이 만료되어 권한이 없고, 리프레시 토큰이 있다면 그 리프레시 토큰을 이용해서 새로운 access token 을 요청
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    refresh_token: getCookie("refresh_token")
                })
            }).then((res) => {
                if (res.ok){
                    return res.json()
                }
            }).then((result) => {
                // refresh token 재발급에 성공하면 로컬 스토리지 값을 새로운 access token 으로 교체
                localStorage.setItem("access_token", result.accessToken)
                // 새로운 access token 으로 http 요청을 보낸다.
                httpRequest(method, url, body, success, fail)
            })
        }
        else {
            return fail()
        }
    }).
}



















