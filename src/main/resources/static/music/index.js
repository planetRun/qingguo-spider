 
var music_list =[];

getTag();

function getTag() {
    $.get('/api/music/catagory',function (result) {
        var arr=result.data;
        console.log(arr);

        for(var i =0;i<arr.length;i++){
            var data = arr[i];
            $("#tag").append(
                "<a onclick='sendMusic("+data.id+")'>"+data.catagory+"" +
                "</a>")

        }
    })
}
function sendMusic(id) {
    $.post("/api/music/random/"+id,function (result) {
        if(result.code ==200){
            $("#musicId").css('display','inline-block')
            var arr=result.data;
            console.log(arr);
            music_list=[];
            $("#play_list").empty()
            for(var i =0;i<arr.length;i++){
                var data = arr[i];
                var music = {
                    "id":data.songId,
                    "name":data.title,
                    "singer":data.author,
                    "duration":"",
                    "src":data.url,
                    "images":data.pic
                }
                music_list.push(music);
            }

            loadPlayList();
        }else{
            layer.msg(result.data,{ icon:2, shade:0.4 });
        }

    })
}
//formateTime(61)--->01:01


//鍒濆鍖�
    //杞藉叆鎾斁鍒楄〃
    
    //鎾斁搴忓彿
    var play_index=0;
    //鍒濆鍖栨樉绀烘挱鏀惧垪琛�
    //褰揻lag涓�1鐨勬椂鍊�,琛ㄧず鍒楄〃鏄剧ず(褰撳墠鐘舵€�)
    //褰揻lag涓�0鐨勬椂鍊�,琛ㄧず鍒楄〃闅愯棌(褰撳墠鐘舵€�)
    var flag=1;
//鑾峰彇鍚勭鏍囩

    var player = document.querySelector("#player");
    var bz_music = document.querySelector("#bz_music");

    //姝屾洸淇℃伅閮ㄥ垎
    var left_photo = document.querySelector("#left_photo");
    var list_title = document.querySelector("#list_title");
    var list_singer = document.querySelector("#list_singer");
    var process_slide = document.querySelector("#process_slide");
    var process = document.querySelector("#process");
    var showHide = document.querySelector("#showHide");
    
    //鎺у埗鎸夐挳閮ㄥ垎
    var time = document.querySelector("#time");
    var btnPlay= document.querySelector("#btnPlay");
    var volume_slide= document.querySelector("#volume_slide");
    var volume= document.querySelector("#volume");
    
    //鎾斁鍒楄〃閮ㄥ垎
    var play_list = document.querySelector("#play_list");
    
    var play_list_area = document.querySelector("#play_list_area");

//鍔ㄦ€佸姞杞芥挱鏀惧垪琛�
    function loadPlayList(){
        //閬嶅巻鎾斁鍒楄〃
        for(var i=0;i<music_list.length;i++){
            //灏嗘瘡涓璞★紝鍒嗗埆瀛樺埌music涓�
            var music = music_list[i];
            //鍒涘缓li鏍囩
            var liTag = document.createElement("li");
            //鍒涘缓姝屾洸鍚峴pan鏍囩
            var spanTitleTag = document.createElement("span");
            //鍒涘缓鏃堕暱span鏍囩
            var spanDurationTag = document.createElement("span");
            
            //涓簎l娣诲姞li鏍囩锛屽瓙鑺傜偣
            play_list.appendChild(liTag);
            //涓簂i鏍囩锛屾坊鍔犲瓙鑺傜偣
            liTag.appendChild(spanTitleTag);
            liTag.appendChild(spanDurationTag);
            
            //娣诲姞鍐呭
            spanTitleTag.innerHTML=music.name;
            spanDurationTag.innerHTML=music.duration;
            
            //娣诲姞绫诲悕
            spanTitleTag.classList.add("list_title");
            spanDurationTag.classList.add("list_time");
            
            //鑷畾涔夊睘鎬�
            //闇€瑕佺敤鐨勬椂鍊欙紝鐩存帴浠庢爣绛句腑鍙栧€硷紝涓嶉渶瑕佸拰鍚庡彴浜や簰
            liTag.setAttribute("data-index",i);
            
            //褰撶偣鍑绘瘡涓€涓猯i鏍囩鐨勬椂鍊�
            //閲嶆柊杞藉叆姝屾洸淇℃伅(涓撹緫鍥剧墖銆佹瓕鏇茶矾寰勩€佹瓕鏇插悕銆佹瓕鎵嬪悕)
            //鎾斁褰撳墠鐐瑰嚮鐨勯煶涔�
            liTag.addEventListener("click",function(){
                //鑾峰彇姣忎釜li鏍囩鐨勬瓕鏇瞚d
                var index = this.getAttribute("data-index");
//              console.log(index);
                //灏嗘瓕鏇瞚d璧嬬粰锛屽叏灞€鍙橀噺play_index
                play_index = index;
                //璋冪敤杞藉叆姝屾洸鍑芥暟
                loadMusic();
                //鎾斁闊充箰
                playMusic();
            })
        }
        $("#play_list li").first().trigger("click")
    }
    
//杞藉叆姝屾洸淇℃伅
    function loadMusic(){
        var music = music_list[play_index];
        //鏀瑰彉涓撹緫鍥剧墖
        left_photo.src = music.images;
        //鏀瑰彉姝屾洸鍚�
        list_title.innerHTML = music.name;
        //鏀瑰彉姝屾墜鍚�
        list_singer.innerHTML = music.singer;
        //鏀瑰彉姝屾洸璺緞
        player.src = music.src;
    }
    
//鎾斁,鏆傚仠闊充箰
    btnPlay.addEventListener("click",function(){
        //paused,琛ㄧず褰撳墠闊充箰鏄惁涓烘殏鍋滅姸鎬�
        if(player.paused){
            //play(),鎾斁褰撳墠闊充箰
            playMusic();
        }
        else {
            //pause(),鏆傚仠褰撳墠闊充箰
            player.pause();
            btnPlay.setAttribute("class","btn_play fa fa-play");
        }
    })

//涓婁竴鏇�
    function backword(){
        if(play_index==0){
            play_index=music_list.length-1;
        }
        else{
            //鏀瑰彉鎾斁搴忓彿
            play_index--;
        }
        //閲嶆柊杞藉叆
        loadMusic();
        //鎾斁
        playMusic();   
    }
    
//涓嬩竴鏇�
    function forward(){
        if(play_index==music_list.length-1){
            play_index=0;
        }
        else{
            //鏀瑰彉鎾斁搴忓彿
            play_index++;
        }
        //閲嶆柊杞藉叆
        loadMusic();
        //鎾斁
        playMusic();   
    }
    
//鎾斁
    function playMusic(){
        player.play();
        btnPlay.setAttribute("class","btn_play fa fa-pause"); 
    }



//鏃堕棿杞崲

    function formateTime(time){
        if(time>3600){
            var hour = parseInt(time/3600);
            var minute = parseInt(time%3600/60);
            var second = parseInt(time%3600);
            hour=hour>=10?hour:"0"+hour;
            minute=minute>=10?minute:"0"+minute;
            second=second>=10?second:"0"+second;
            return hour+":"+minute+":"+second;
        }
        else{
            var minute = parseInt(time/60);
            var second = parseInt(time%60);
            minute=minute>=10?minute:"0"+minute;
            second=second>=10?second:"0"+second;
            return minute+":"+second;  
        }

    }
    
//璁剧疆瀹氭椂鍣�
    window.setInterval(function(){
        //currentTime,褰撳墠鎾斁鐨勭鏁�!
//      console.log(player.currentTime);
        time.innerHTML = formateTime(player.currentTime);
        //duration,褰撳墠闊充箰鐨勬€绘椂闀�,绉掓暟!!!
        var percent = player.currentTime/player.duration;
//      console.log(percent);
        process_slide.style.width=percent*100+"%";
    },100)
    
//闈欓煶
    function volumeOff(){
        //volume,[0,1]
        player.volume=0;
        volume_slide.style.width=0;
        console.log(player.volume);
    }
    
//鏈€澶ч煶 
    function volumeUp(){
        player.volume=1;
        volume_slide.style.width="100%";
        console.log(player.volume);
    }

//閫氳繃婊戝潡鎺у埗闊抽噺澶у皬
    volume.addEventListener("click",function(event){
        //寰楀埌褰撳墠鐐瑰嚮鐨勪綅缃�
        var currentVolume = event.offsetX/this.offsetWidth;
        console.log(currentVolume);
        //璁剧疆闊抽噺
        player.volume=currentVolume;
        volume_slide.style.width = currentVolume*100+"%";
    })

//閫氳繃婊戝潡鎺у埗闊充箰杩涘害
    process.addEventListener("click",function(event){
        //璁＄畻鐐瑰嚮浣嶇疆鐨勭櫨鍒嗘瘮
        var currentValue = event.offsetX/this.offsetWidth;
        
        //鍥犱负鎴戜滑宸茬粡璁剧疆浜嗗畾鏃跺櫒,鍦ㄥ疄鏃剁洃鎺ф垜浠綋鍓嶉煶涔愮殑鍙樺寲
        //鍥犳,鎴戜滑閫氳繃璁剧疆褰撳墠鎾斁鐨勯煶涔愭椂闀�,褰卞搷鎴戜滑鐨勮繘搴︽潯
        player.currentTime = player.duration*currentValue;
    })

//鏄剧ず闅愯棌鎾斁鍒楄〃
    function showMusicList(){
        //褰撳墠宸茬粡鏄剧ず鎾斁鍒楄〃
        if(flag){
            play_list_area.style.display="none";
            bz_music.style.width="500px";
            showHide.style.color="#666";
            flag=0;
        }
        else {
            play_list_area.style.display="block";
            bz_music.style.width="700px";
            showHide.style.color="#DDD";
            flag=1;
        }
    }

    //鍒濆闊抽噺
    player.volume=0.5;

