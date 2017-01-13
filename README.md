## Firebase를 활용한 로그인, 회원가입 및 채팅 앱



#### 1. firebase SDK

시작하기 앞서 Firebase SDK를 추가합니다.

```
buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:3.0.0'
    }
}
```

```
apply plugin: 'com.google.gms.google-services'

dependencies {
  compile 'com.google.firebase:firebase-auth:10.0.1'
  compile 'com.google.firebase:firebase-database:10.0.1'
}
```



#### 2. 회원가입

```
FirebaseAuth auth = FirebaseAuth.getInstance();

Auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    //사용자 생성 실패
                }
            }
        });
```

`createUserWithEmailAndPassword`를 통해 email과 password를 전달합니다. 그리고 `OnCompleteListener`를 통해 생성 성공 혹은 실패를 알 수 있습니다. 생성 성공 시 해당 사용자는 로그인 처리가 되며 `AuthStateListener`가 `onAuthStateChanged` 콜백을 실행합니다. 이를 통해 사용자 계정 정보를 불러올 수 있습니다.