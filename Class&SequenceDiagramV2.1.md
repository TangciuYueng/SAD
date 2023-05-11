```puml
Class01 <|-- Class02: 继承
Cf <|.. Ci: 实现
Class03 *-- Class04: 组合
Class05 o-- Class06: 聚合
A <-- B: 关联
C <.. D: 依赖
```
## 注册登录
### 类图
```puml
class LoginController{
    + searchInfo()
}
class LoginPage{
    + Login: Button
    + Signup: Button
    + Email: TextBox
    + Password: TextBox
    
    + checkInfo()
    + openRegister()
}
LoginController <.. LoginPage

class RegisterController{
    + createUser()
    + findInfo()
}
class RegisterPage{
    + Register: Button
    + Information: textBox
    + verifyRegister(info)
}
RegisterController <.. RegisterPage
```
### 交互图
```puml
@startuml
autonumber

actor Visitor as v
boundary ":LoginInterface" as li
control ":LoginController" as lc
boundary ":RegisterInterface" as ri
control ":RegisterController" as rc
entity User as u

mainframe Login&Register

v -> li: click login button
activate li

li -> lc: checkInfo()
activate lc

lc -> u: searchInfo()
activate u

u --> lc: return login status
deactivate u

alt PasswordConrrect
    lc --> li: login success
    li --> v: open homepage
    lc  -> u: changeStatus()
    activate u

    deactivate u
    
else else
    lc --> li: login failure
    li --> v: send Login Failure Message
    end

deactivate li
deactivate lc

v -> li: click register button
activate li
li -> ri: jump page
deactivate li
activate ri
ri -> rc: verifyInfo(info)
activate rc
rc -> u: searchInfo()
activate u
u --> rc: return existence
deactivate u

alt userExist
    rc --> ri: register failure
    ri --> v: send login failure message
else else
    rc -> u: createUser()
    rc --> ri: register success
    deactivate rc
    ri --> v: open login page
    deactivate ri
    end
@enduml
```
## 音乐推荐
```puml
class Visitor{
    + VisitorID: string
    + searchMusic()
}
class User{
    + userID: string
    + userName: string
    + email: string
    - password: string
    - phone: string

    + getRecommendedMusic()
    + sendMood

    + getUserInfo(): content
    + setUserInfo()
    + createMusicroom()
    + joinMusicroom()
}
class Admin{
    + setSystemRecommendedMusic()
}

class MusicHomePage{
    + moodList: list
    + recommendedMusic: list
    + searchBar: TextBox

    + UserID: string
    + UserName: string
    + UserFriendNum: uint
    + UserMoodValue: int
    + UserVisitor: uint
    + UserSongList: list

    + startMusicButton: Button
    + pauseMusicButton: Button
    + nextMusicButton: Button
    
    + localMusicButton: Button
    + userFollowingButton: Button
    + userLikeButton: Button
    + userRecentListenButton: Button

    + startMusic()
    + pauseMusic()
    + nextMusic()

    + getLocalMusic()
    + getUserFollowing()
    + getUserLike()
    + getUserRecentListen()

    + getRecommendedMusic()
}

class MusicHomePageController{
    + searchRecommendedMusic()
    + checkUserDB()
    + checkMusicDB()
}


Visitor <|-- User
User <|-- Admin

Visitor -- MusicHomePage
User -- MusicHomePage
Admin -- MusicHomePage

MusicHomePage ..> MusicHomePageController
```
### 交互图
#### 搜索&听音乐
```puml
@startuml
autonumber

actor User as u
boundary ":SearchInterface" as si
control ":SearchController" as sc
control ":MusicCommender" as mc
database Music as m

mainframe Search&ListenMusic
u -> si: input the music name
activate si
u -> si: click the search button

si -> sc: send the request
deactivate si
activate sc
sc -> m: SearchMusic(music)
deactivate sc
activate m

alt Found
    m --> sc: return targeted music
    activate sc
    sc --> si: answer the request
    deactivate sc
    activate si
    si --> u: listen the music
    deactivate si
else else
    m --> sc: return None
    deactivate m
    activate sc
    sc --> si: messagebox the result
    deactivate sc
    end

@enduml
```
#### 音乐推荐至个人

```puml
@startuml
autonumber

actor User as u
boundary ":HomeInterface" as hi
control ":HomeController" as hc
control ":MusicRecommender" as mr
database Music as m

mainframe RecommendMusic
u -> hi: click mood clock-in button
activate hi
u -> hi: select the mood
hi -> hc: send the data
deactivate hi
activate hc
hc -> mr: getRecommendedMusic(data)
deactivate hc
activate mr
mr -> m: getMusic
activate m
m --> mr: return music
deactivate m
mr --> hi: show recommended music
deactivate mr



@enduml
```
## 个人空间
### 类图
```puml
class User{
    + userID: string
    + userName: string
    + email: string
    - password: string
    - phone: string

    + getRecommendedMusic()
    + sendMood

    + getUserInfo(): content
    + setUserInfo()
    + createMusicroom()
    + joinMusicroom()
}
class UserJournalPage{

}
class JournalSearchController{

}
class JournalSendController{

}
class Comment{
    + senderID: string
    + content: string
    + sendTime: time

    + setInfo()
}
class UserHomePage{

}
class UserHomePageController{
    
}
class JournalIdtorPage{

}
class EssaySquarePage{

}
```
### 交互图
#### 编辑并发布随笔
```puml
@startuml
autonumber

actor User as u
actor Admin as ad
boundary ":ReviewInterface" as ri
control ":ReviewController" as rc
boundary ":ApplicationInterface" as ai
control ":ApplicationController" as ac
entity Application as a
boundary ":UserHomeInterface" as hi
boundary ":JournalIdtorInterface" as jii
control ":JournalController" as jc
entity Journal as j

mainframe Edit&PostJournal

u -> hi: click edit button
activate hi
hi -> jii: jump page
deactivate hi
activate jii
u -> jii: add content
deactivate jii 

u -> ai: click post button
activate ai
ai -> ac: createApplication(applicationVO)
deactivate ai
activate ac
ac -> a: createApplication(application)
activate a
deactivate a
deactivate ac
deactivate ai

ad -> ri: reviewApplication()
activate ri
ri -> rc: sendRequest(reviewVO)
activate rc
rc -> a: get applications DTO
activate a
a --> rc: send applications DTO
deactivate a
rc --> ri: return application list VO
deactivate rc
ri --> ad: showApplication()
deactivate ri


alt applicationApproved
    ad -> ri: approveApplication()
    activate ri
    ri -> rc: send approval
    activate rc
    rc -> jc: sendJournalCreateAppliction(application)
    activate jc
    rc -> a: deleteApplication(application)
    activate a
    deactivate a
    jc -> j: createJournal(journal)
    activate j
    deactivate j
    deactivate jc
    deactivate ri
    rc --> ai: return approval
    activate ai
    rc --> hi: showJournal()
    activate hi
    deactivate rc
    deactivate hi
    ai --> u: messagebox post success
    deactivate ai
else else
    ad -> ri: denyApplication()
    activate ri
    ri -> rc: send denial
    deactivate ri
    activate rc
    rc -> a: deleteApplication(application)
    activate a
    deactivate a
    rc --> ai: return denial
    deactivate rc
    activate ai
    ai --> u: send application failure information
    deactivate ai

    end

@enduml
```
#### 浏览或评论随笔
```puml
@startuml
autonumber

actor User as u
actor Admin as ad
boundary ":ReviewInterface" as ri
control ":ReviewController" as rc
boundary ":ApplicationInterface" as ai
control ":ApplicationController" as ac
entity ":Application" as a
boundary ":EssaySquareInterface" as ei
boundary ":CommentEditorInterface" as cii
control ":CommentController" as cc
entity Comment as c

mainframe browse&comment

u -> ei: click to comment journal
activate ei
ei -> cii: jump page
deactivate ei
activate cii

u -> ai: click post button
activate ai
deactivate cii
ai -> ac: createApplication(applicationVO)
deactivate ai
activate ac
ac -> a: createApplication(application)
activate a
deactivate a
deactivate ac
deactivate ai

ad -> ri: reviewApplication()
activate ri
ri -> rc: sendRequest(reviewVO)
activate rc
rc -> a: get applications DTO
activate a
a --> rc: send applications DTO
deactivate a
rc --> ri: return application list VO
deactivate rc
ri --> ad: showApplication()
deactivate ri


alt applicationApproved
    ad -> ri: approveApplication()
    activate ri
    ri -> rc: send approval
    activate rc
    rc -> cc: sendCommentCreateAppliction(application)
    activate cc
    rc -> a: deleteApplication(application)
    activate a
    deactivate a
    cc -> c: createComment(comment)
    activate c
    deactivate c
    deactivate cc
    deactivate ri
    rc --> ai: return approval
    activate ai
    rc --> ei: showComment()
    activate ei
    deactivate rc
    deactivate ei
    ai --> u: messagebox post success
    deactivate ai
else else
    ad -> ri: denyApplication()
    activate ri
    ri -> rc: send denial
    deactivate ri
    activate rc
    rc -> a: deleteApplication(application)
    activate a
    deactivate a
    rc --> ai: return denial
    deactivate rc
    activate ai
    ai --> u: send application failure information
    deactivate ai

    end
@enduml
```
## 音乐室
### 类图
```puml
class User{
    + userID: string
    + userName: string
    + email: string
    - password: string
    - phone: string

    + getRecommendedMusic()
    + sendMood

    + getUserInfo(): content
    + setUserInfo()
    + createMusicroom()
    + joinMusicroom()
}
class MusicroomMember{
    + applySinging()
    + quitMusicroom()
    + singSong()
}
class MusicroomLeader{
    + disbandMusicroom()
    + manageSingingList()
}



class ReviewPage{
    + musicroomInfo: TextBox
    + musciroomList: list
    + approveButton: Button
    + denyButton: Button

    + sendResult()
    + returnResult()
    + showMusicroom()
    + sendRequest()
}
class ReviewController{
    + checkMusicroomDB()
    + sendMusicroomCreateApplication()
    + deleteMusicroom()
    + getMusicroom()
}
class MusicroomPage{
    + musicroomName: string
    + memberNumber: uint

    + musicroomList: list
    + friendList: list
    + musicMember: list

    + musicSet: set
    + currentLyrics: string
    + currentMusicName: string

    + waitQueue: list

    + applySingingButton: Button
    + addMusicButton: Button
    + sendEmojiButton: Button
    + sendImageButton: Button
    + sendGiftButton: Button

    + applySinging()
    + addMusic()
    + sendEmoji()
    + sendImage()
    + sendGift()
}
class MusicroomPageController{
    + checkMusicroomDB()
    + sendMusicroomCreateApplication()
    + deleteMusicroom(musicroom)
    + setMusicroomLeader(leaderInfo)
}
class Musicroom{
    + musicroomID: string
    + musicroomName: string
    + musicroomLeaderID: string
    + musicroomLeaderName: string
    + musicroomMember: list
    + isDeleted: bool
    + memberNumber: uint

    + getMusicroomInfo()
    + setMusicroomInfo()
    + getMemberList()
}
class MusicroomController{
    + createMusicroom(musicroom)
    + deleteMusicroom(musicroom)
}
User -- ReviewPage
User -- MusicroomPage
MusicroomMember -- MusicroomPage
MusicroomLeader -- ReviewPage
MusicroomLeader -- MusicroomPage

ReviewPage ..> ReviewController
MusicroomPage ..> MusicroomPageController

ReviewController ..> Musicroom

ReviewController ..> MusicroomController
MusicroomPageController ..> MusicroomController

Musicroom <.. MusicroomPageController

User <|-- MusicroomMember
MusicroomMember <|-- MusicroomLeader
```
### 交互图
#### 创建音乐室
```puml
@startuml
autonumber

actor User as u
actor Admin as ad
boundary ":ApplicationInterface" as ai
control ":ApplicationController" as ac
boundary ":ReviewInterface" as ri
control ":ReviewController" as rc
control ":MusicroomController" as mrc
entity Application as a
entity Musicroom as mr

mainframe CreateMusicroom

u -> ai: click to create a musicroom \n and fill in information \n submit application
activate ai
ai -> ac: createApplication(applicationVO)
activate ac
ac -> a: createApplication(application)
activate a
deactivate a
deactivate ac
deactivate ai

ad -> ri: reviewApplication()
activate ri
ri -> rc: sendRequest(reviewVO)
activate rc
rc -> a: get applications DTO
activate a
a --> rc: send applications DTO
deactivate a
rc --> ri: return application list VO
deactivate rc
ri --> ad: showApplication()
deactivate ri

alt applicationApproved
    ad -> ri: approveApplication()
    activate ri
    ri -> rc: send approval
    activate rc
    rc -> mrc: sendMusicroomCreateAppliction(application)
    activate mrc
    rc -> a: deleteApplication(application)
    activate a
    deactivate a
    mrc -> mr: createMusicroom(musicroom)
    activate mr
    deactivate mr
    deactivate mrc
    deactivate ri
    rc --> ai: return approval
    activate ai
    deactivate rc
    ai --> u: open musicroom page
    deactivate ai
else else
    ad -> ri: denyApplication()
    activate ri
    ri -> rc: send denial
    deactivate ri
    activate rc
    rc -> a: deleteApplication(application)
    activate a
    deactivate a
    rc --> ai: return denial
    deactivate rc
    activate ai
    ai --> u: send application failure information
    deactivate ai

    end

@enduml
```
#### 解散音乐室
```puml
@startuml
autonumber

actor MusicroomLeader as mla
entity MusicroomLeader as ml
boundary ":ApplicationInterface" as ai
control ":ApplicationController" as ac
control ":MusicroomController" as mrc
entity Musicroom as mr

mainframe DismissMusicroom
mla -> ai: click dismiss button
activate ai
ai -> ml: isMusicroomLeader(): bool
activate ml
ml --> ai: return the leader status
deactivate ml
deactivate ai

alt is the musicroom leader
    ai -> ac: sendApplication(applicationVO)
    activate ac
    ac -> mrc: sendMusicDismissApplication()
    deactivate ac
    activate mrc
    mrc -> mr: deleteMusicroom()
    activate mr
    deactivate mr
    mrc --> ai: return success information
    activate ai
    deactivate mrc
    ai --> mla: close musicroom page
    deactivate ai
else else
    ai --> ai: return denial
    ai --> mla: messagebox denial
    end
@enduml
```
#### 申请唱歌
```puml
@startuml
autonumber

actor MusicroomMember as mrm
actor MusicroomLeader as mrl
boundary ":ReviewInterface" as ri
control ":ReviewController" as rc
boundary ":ApplicationInterface" as ai
control ":ApplicationController" as ac
entity Application as a
control ":MusicroomController" as mrc
entity Musicroom as mr

mainframe ApplySinging
mrm -> ai: applySinging(songs)
activate ai
ai -> ac: sendApplication(applicationVO)
deactivate ai
activate ac
ac -> a: createApplication(application)
deactivate ac
activate a



mrl -> ri: reviewApplication()
activate ri
deactivate a
ri -> rc: sendRequest(reviewVO)
activate rc
rc -> a: get application DTO
activate a
a --> rc: send application DTO
deactivate a
rc --> ri: return application list VO
deactivate rc
ri --> mrl: showApplication()
deactivate ri

alt applicationApproved
    mrl -> ri: approveApplication()
    activate ri
    ri -> rc: send approval
    deactivate ri
    activate rc
    rc -> mrc: sendMusicroomSingingApplication(application)
    activate mrc
    rc -> a: deleteApplication(application)
    rc -> ai: return approval
    deactivate rc
    activate ai
    ai --> mrm: application success
    deactivate ai
    activate a    
    mrc -> mr: addWaitQueue(song)
    activate mr
    deactivate mrc
    deactivate mr
    deactivate a
else else
    mrl -> ri: denyApplication()
    activate ri
    ri -> rc: send denial
    deactivate ri
    activate rc
    rc -> a: deleteApplication(application)
    
    activate a
    deactivate a
    rc -> ai: return denial
    activate ai
    ai --> mrm: application failure
    deactivate ai
    deactivate rc
    end
@enduml
```