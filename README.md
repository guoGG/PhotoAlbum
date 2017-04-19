# PhotoAlbum


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
		compile 'com.github.guoGG:PhotoAlbum:1.0.0'
	}



    <!--获取手机存储卡权限-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <!--获取sd卡写的权限，用于文件上传和下载-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />



     <activity android:name="com.example.gjw.albumlibrary.album.PhotoAlbumActivity"/>
     <activity android:name="com.example.gjw.albumlibrary.album.FloderActivity"/>
