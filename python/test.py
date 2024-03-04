import subprocess

def install_requirements(requirements_file):
    with open(requirements_file, 'r') as f:
        packages = f.readlines()

    for package in packages:
        package = package.strip()
        subprocess.call(['pip', 'install', package])

    print("Installed packages from requirements file")


def get_post():
    url = 'https://jsonplaceholder.typicode.com/posts/10'
    response = requests.get(url)

    if response.status_code == 200:
        print("Response JSON:")
        print(response.json())
    else:
        print(f"Error: {response.status_code}")


if __name__ == "__main__":
    print("Message from separate py file")
