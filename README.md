## junior software engineer blog

## reference 
### rear end
https://spring.io/guides/gs/serving-web-content/
### front end
https://v3.bootcss.com/getting-started/#download
https://v3.bootcss.com/css/#grid

## github login
https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app
## github search
https://docs.github.com/en/rest/reference/search


### find issues example
https://api.github.com/search/issues?q=+label:good-first-issue+state:open&sort=created&order=asc
https://api.github.com/search/issues?q=java+label:%22good%20first%20issue%22+state:open&sort=created&order=asc&per_page=100&page=2
### find StackOverflow
https://api.stackexchange.com/docs/advanced-search#page=1&pagesize=10&order=desc&sort=activity&q=Complete%20the%20NavBar&filter=default&site=stackoverflow&run=true

/2.3/search/advanced?page=1&pagesize=10&order=desc&sort=activity&q=Complete the NavBar&site=stackoverflow

### find repositories example
https://api.github.com/search/repositories?q=tetris+language:assembly&sort=stars&order=desc
#### recommend repositories
https://api.github.com/search/repositories?q=Tetris+language:assembly+in:readme,description+watchers>2&sort=stars&order=desc
first-contributions
### find topic example
https://api.github.com/search/topics?q=ruby&per_page=100&page=2

#### find contribute issues
https://api.github.com/search/issues?q=is:issue+author:twsswt

####
CREATE TABLE IssueHistory(
   id INT UNSIGNED AUTO_INCREMENT,
   userid  VARCHAR(100),
   html_url VARCHAR(200),
title    VARCHAR(200) ,
lables VARCHAR(200) ,
q VARCHAR(40) ,
time TIMESTAMP,
   PRIMARY KEY (id)
)

CREATE TABLE RepositoryHistory(
   id INT UNSIGNED AUTO_INCREMENT,
   userid  VARCHAR(100),
   html_url VARCHAR(200),
title    VARCHAR(200) ,
language VARCHAR(200) ,
q VARCHAR(40) ,
time TIMESTAMP,
   PRIMARY KEY (id)
)


CREATE TABLE FavoriteList(
   id INT UNSIGNED AUTO_INCREMENT,
   user VARCHAR(100),
   issuetitle VARCHAR(200),
html_url VARCHAR(200) ,
keyword VARCHAR(100) ,
programming VARCHAR(10) ,
p_sort VARCHAR(10) ,
p_order VARCHAR(10) ,
github VARCHAR(10) ,
stackoverflow VARCHAR(10) ,
commits VARCHAR(200) ,
type VARCHAR(200) ,
time TIMESTAMP,
   PRIMARY KEY (id)
)