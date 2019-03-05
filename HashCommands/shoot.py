from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import os
import sys

path = sys.argv[1]

#TODO: Zip files and create sources.zip in /output directory (path)

driver = webdriver.Chrome()

print("Waiting for you to access the website and press [enter]")
input()
print("Starting upload")

createSubmissionButton = driver.find_element(By.XPATH, '/html/body/div[1]/div/div/md-content/div[1]/md-card/md-card-header/div/button')
createSubmissionButton.click()

judgeUploads = driver.find_elements_by_tag_name("judge-upload")

#TODO: name files accordingly
#files = ["sources.zip","A.in","B.in","C.in","D.in"]
files = ["kittens.in","kittens.in","kittens.in","kittens.in","kittens.in"]

ct = 0
for judgeUpload in judgeUploads:
    input = judgeUpload.find_element_by_xpath("//input[@type='file']")
    filePath = path + "/" + files[ct]
    input.send_keys(filePath)
    print("Sent: " + filePath)
    ct+=1