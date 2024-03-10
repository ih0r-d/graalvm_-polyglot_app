import requests

def get_post():
    import requests

    url = 'https://jsonplaceholder.typicode.com/posts/10'
    response = requests.get(url)

    if response.status_code == 200:
        print("Response JSON:")
        print(response.json())
    else:
        print(f"Error: {response.status_code}")


if __name__ == "__main__":
    print("Start running separate py file")
    get_post()
