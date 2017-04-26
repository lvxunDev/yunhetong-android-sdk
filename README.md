# 云合同Android客户端SDK集成文档
---
[TOC]

>注：本文为第三方app集成云合同SDK的使用教程，默认开发者具有一定的android应用开发技能。


##1 SDK导入

下载[云合同SDK][1] 并解压缩。
    
###1.1  Android studio导入

将解压的SDK项目放置在与app目录同级的目录下，然后编辑 ```setting.gradle```文件.

如：在 app 同级的目录下放置了 yhtsdk
的开源库文件夹，然后编辑  ```setting.gradle```，内容改为：

    include ':yhtsdk' 

然后再回到你的 app 目录下的 ```build.gradle```
文件，在 ```dependencies```节点下加入：

    dependencies {
        compile project(':yhtsdk')
    }

完成以后，成功添加yhtsdk Module。


###1.1 Eclipse导入

导入SDK项目后，需要导入官方提供的```android-support-v7-appcompat```底包，

层级为 第三方app依赖yhtsdk,yhtsdk依赖```android-support-v7-appcompat```

##2  三分钟快速集成###
第三方app快速集成云合同sdk所提供的功能，

###2.1 集成准备

####2.1.1 获取AppId

登录[云合同SDK开发者平台][1]，注册第三方应用，获取AppId

在第三方app应用AndroidManifest.xml中appliction节点内
        

    <meta-data android:name="YhtSdk_AppId" android:value="id_(获取的appid)"/> //注意添加前缀"id_"
####2.1.2 切换环境
将SDK中YhtContent 的YHT_HOST 切换成正式环境的host。

	public static final String YHT_HOST = "http://sdk.yunhetong.com";

####2.1.3 app主题设置
在```AndroidManifest.xml```中添加app主题。

    <application android:theme="@style/SDKAppTheme">

如果app有自定义主题，可以选择将自定义的Theme 设置为

    <style name="myAppTheme" parent="SDKAppTheme">

若有冲突，不能用上述办法，可将主题Theme添加至sdk中具体的activity节点上，如：
   
    <activity
            android:name="com.yunhetong.sdk.***Activity"
            android:theme="@style/SDKAppTheme"/>

准备完毕。
###2.2 初始化云合同SDK

第三方开发者自定义Application类，在onCreat方法中初始化SDK

	// 云合同SDK初始化
     @Override
    public void onCreate() {
        super.onCreate();
        // 云合同SDK初始化
        YhtSdk.getInstance().initYhtSdk(this, new Token.TokenListener() {
            @Override
            public void onToken(Action action) {
                //获取token的异步操作
            }
        });
    }

获取Token的请求，获取token请求成功并返回token后，初始化token数据

    //初始化Token 
    YhtSdk.getInstance().setToken(tokenStr, action);

初始化基本完成,详情步骤参考simple-demo


####2.3 集成合同模块

快速集成**合同查看，签署，作废功能**，可使用SDK提供的```ContractDetailActivity```

    /**
     * 如果第三方是从Activity跳转过来的 调用本方法
     * 请求码是 ContractDetailActivity.REQUEST_CODE
     * @param act/fg
     * @param contractId 用户合同Id
     */
    ContractDetailActivity.gotoContractDetailActForResult(Activity act, String contractId);
   
    ContractDetailActivity.gotoContractDetailActForResult(Fragment fg, String contractId);

上述方法有返回结果在```onActivityResult()```方法中，接收返回数据

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ContractDetailActivity.request_code) {
            if (resultCode == ContractDetailActivity.result_code_invalid) {
                //作废成功 
            }
            if (resultCode == ContractDetailActivity.result_code_signfinish) {
                //签署成功
            }
        }
    }


快速集成**签名的查看，删除功能**，可使用SDK提供的```SignDetailActivity```

静态方法进入，传入相应的参数即可。


快速集成**签名的绘制功能**，可使用SDK提供的```SignGeneratorActivity```

静态方法进入，传入相应的参数即可，有返回结果，参考demo。

##3  自定义集成

云合同sdk功能接口对外开放，YhtSdk.class类提供sdk所有的接口，
对第三方app对合同和签名操作的管理，接入方可自定义采用其中的接口完成对接。

###3.1 合同模块功能
合同管理模块是指第三方App对合同进行查看、预览、签署、作废操作的功能。

####3.1.1 合同查看
用户合同信息请求，返回合同详细信息
 
    new SdkRequestManaer().contractDetail( contractId, 
                                           notificaParams, 
                                           requestCode,
                                           onCallBackListener)；
加载合同页

    String contractUrl = new SdkRequestManaer().getContractUrl(contractId)；
    YhtWebView.loadUrl(contractUrl);
    

####3.1.2 合同签署

    new SdkRequestManaer().contractSign(contractId,  
                                        requestCode,
                                        onCallBackListener)；
   
####3.1.3 合同作废

对接用户对至少有一人已签署但尚未签署完成的合同执行的作废的功能。
    
    new SdkRequestManaer().contractInvalid(contractId,  
                                           requestCode,
                                           onCallBackListener)；
    
###3.2 签名操作基本功能
云合同签名管理模块是指第三方App对用户的签名信息进行操作的功能。


####3.2.1 签名查看
用户查看签名请求，返回签名详情信息，具体在YhtSign类中查看
 
    new SdkRequestManaer().signDetail(requestCode,onCallBackListener);
    
####3.2.2 签名创建
Sdk提供签名绘制面板YhtSignDrawView，有获取签名数据和清除签名的
用户绘制签名，并可对绘制签名执行采用或清除的功能

    new SdkRequestManaer().signGenerate(signData,requestCode,onCallBackListener);

####3.2.3 签名删除
签名管理页面对没有使用过的签名进行删除功能
 
    new SdkRequestManaer().signDelete(requestCode,onCallBackListener);
   

##4 类的说明

可能使用到的类说明

| **Class**                     |            **Class description**                          |

|-------------------------------|-----------------------------------------------------------|

|   YhtSdk                      |  SDK的入口类。提供SDK初始化,token初始化，相关请求的方法。        |

|   YhtContent                  |  常量类，提供接口URL,请求码，常量                            |

|   RespondObject               |  网络响应类                             |

|   YhtContract                 |  合同详情类                                |

|   YhtContractParter           |  合同附加信息类                                          |

|   YhtSign                     |  签名类                                |

|   ContractDetailActivity      |  合同详情页，集成合同查看、作废、签署功能                     |

|   ContractPreviewActivity     |  合同预览页，集成合同查看、合同签署功能                       |

|   SignDetailActivity          |  签名信息页，集成签名查看、签名删除功能                |

|   SignGeneratorActivity       |  新增签名页，集成签名绘制画板，签名创建功能                |

|   YhtSignDrawView             |  签名画板                                                 |

|   YhtWebView                  |  WeBView                                                  |


  [1]: http://sdk.yunhetong.com/sdk/open/userApp/appManageView

  
