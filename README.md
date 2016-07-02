## 写在前面
要学习新东西，最好的办法是先学会如何使用。所以，本文仅作 Android Data Binding 的介绍并结合 [DataBindingDemo][DataBindingDemo] 来理解它的用法，后续再对其原理进行深入探讨。

## 简介
Data binding 在2015年7月发布的Android Studio v1.3.0 版本上引入，在2016年4月Android Studio v2.0.0 上正式支持。目前为止，Data Binding 已经支持双向绑定了。

Databinding 是一个实现数据和UI绑定的框架，是一个实现 MVVM 模式的工具，有了 Data Binding，在Android中也可以很方便的实现MVVM开发模式。

Data Binding 是一个support库，最低支持到Android 2.1（API Level 7+）。

Data Binding 之前，我们不可避免地要编写大量的毫无营养的代码，如 findViewById()、setText()，setVisibility()，setEnabled() 或 setOnClickListener() 等，通过 Data Binding , 我们可以通过声明式布局以精简的代码来绑定应用程序逻辑和布局，这样就不用编写大量的毫无营养的代码了。

<!-- More -->
## 构建环境
1. 首先，确保能使用Data Binding，需要下载最新的 Support repository。否则可能报错，如图：
    
    ![](https://raw.githubusercontent.com/ConnorLin/BlogImages/master/Android%20Data%20Binding/build_err.png)

2. 在模块的build.gradle文件中添加dataBinding配置

        android {
            ....
            dataBinding {
                enabled = true
            }
        }

    > 注意：如果app依赖了一个使用 Data Binding 的库，那么app module 的 build.gradle 也必须配置 Data Binding。

## Data Binding 布局文件 - (View)
Data binding 的布局文件与传统布局文件有一点不同。它以一个 layout 标签作为根节点，里面是 data 标签与 view 标签。view 标签的内容就是不使用 Data Binding 时的普通布局文件内容。以下是一个例子：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
        <!-- 变量user， 描述了一个布局中会用到的属性 -->
       <variable name="user" type="com.connorlin.databinding.model.User"/>
   </data>

   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">

       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.firstName}"/>

        <!-- 布局文件中的表达式使用 “@{}” 的语法 -->
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"/>
   </LinearLayout>
</layout>
```

### 数据对象 - (Model)
假设你有一个 plain-old Java object(POJO) 的 User 对象。

```java
public class User {
   private final String mFirstName;
    private final String mLastName;
    private int mAge;

    public User(String firstName, String lastName, int age) {
        mFirstName = firstName;
        mLastName = lastName;
        mAge = age;
    }
}
```
或者是 JavaBean 对象：

```java
public class User {
   private final String mFirstName;
    private final String mLastName;
    private int mAge;

    public User(String firstName, String lastName, int age) {
        mFirstName = firstName;
        mLastName = lastName;
        mAge = age;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public int getAge() {
        return mAge;
    }
}
```

从 Data Binding 的角度看，这两个类是一样的。用于 TextView 的 `android:text` 属性的表达式`@{user.firstName} `，会读取 POJO 对象的 `firstName` 字段以及 JavaBeans 对象的 `getFirstName() `方法。

### 绑定数据 - (ViewModel)
在默认情况下，会基于布局文件生成一个继承于 `ViewDataBinding` 的 Binding 类，将它转换成帕斯卡命名并在名字后面接上`Binding`。例如，布局文件叫 `main_activity.xml `，所以会生成一个 `MainActivityBinding` 类。这个类包含了布局文件中所有的绑定关系，会根据绑定表达式给布局文件赋值。在 inflate 的时候创建 binding 的方法如下：

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   
   //  ActivityBaseBinding 类是自动生成的
   ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);
   User user = new User("Connor", "Lin");
   // 所有的 set 方法也是根据布局中 variable 名称生成的
   binding.setUser(user);
}
```

***

### 事件处理
本部分源码请参考 [DataBindingDemo][DataBindingDemo] -> `EventActivity` 部分。

类似于 android:onClick 可以指定 Activity 中的函数，Data Binding 也允许处理从视图中发送的事件。

有两种实现方式：

+ 方法调用
+ 监听绑定

> 二者主要区别在于方法调用在编译时处理，而监听绑定于事件发生时处理。

#### 方法调用
相较于 android:onClick ，它的优势在于表达式会在编译时处理，如果函数不存在或者函数签名不对，编译将会报错。

以下是个例子：

```java
public class EventHandler {
    private Context mContext;
    public EventHandler(Context context) {
        mContext = context;
    }

    public void onClickFriend(View view) {
        Toast.makeText(mContext, "onClickFriend", Toast.LENGTH_LONG).show();
    }
}
```

表达式如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="handler"
            type="com.connorlin.databinding.handler.EventHandler"/>
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:onClick="@{handler::onClickFriend}"/>
        <!-- 注意：函数名和监听器对象必须对应 -->
        <!-- 函数调用也可以使用 `.` , 如handler.onClickFriend , 不过已弃用 -->
    </LinearLayout>
</layout>
```

#### 监听绑定
监听绑定在事件发生时调用，可以使用任意表达式

> 此功能在 Android Gradle Plugin version 2.0 或更新版本上可用.

在方法引用中，方法的参数必须与监听器对象的参数相匹配。在监听绑定中，只要返回值与监听器对象的预期返回值相匹配即可。

以下是个例子：

```java
public void onTaskClick(Task task) {
    task.run();
}
```

表达式如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
  <layout xmlns:android="http://schemas.android.com/apk/res/android">
      <data>
          <variable
            name="handler" type="com.connorlin.databinding.handler.EventHandler"/>
        <variable
            name="task" type="com.connorlin.databinding.task.Task"/>
      </data>

      <LinearLayout 
        android:layout_width="match_parent" 
        android:layout_height="match_parent">
          <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:onClick="@{() -> handler.onTaskClick(task)}"/>
      </LinearLayout>
  </layout>
```

当一个回调函数在表达式中使用时，数据绑定会自动为事件创建必要的监听器并注册监听。

##### 关于参数

+ 参数有两种选择：要么不写，要么就要写全。

```xml
<Button 
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:onClick="@{() -> handler.onTaskClick(task)}" />
或
<Button 
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:onClick="@{(view) -> handler.onTaskClick(task)}"/>
```

+ lambda 表达式可添加一个或多个参数，同时参数可任意命名
    
```java
public class EventHandler {
    public void onTaskClickWithParams(View view, Task task) {
        task.run();
    }
}
```
```xml
<Button 
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:onClick="@{(theview) -> handler.onTaskClickWithParams(theview, task)}" />
```
或者

```java
public class EventHandler {
    public void onCompletedChanged(Task task, boolean completed) {
        if(completed) {
            task.run();
        }
    }
}
```
```xml
<CheckBox 
    android:layout_width="wrap_content" 
    android:layout_height="wrap_content"
    android:onCheckedChanged="@{(cb, isChecked) -> handler.onCompletedChanged(task, isChecked)}" />
```

+ 表达式结果有默认值 null、0、false等等

+ 表达式中可以使用void

```xml
<Button 
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}" />
```

##### 关于表达式

+ 复杂的表达式会使布局难以阅读和维护，这种情况我们最好将业务逻辑写到回调函数中

+ 也有一些特殊的点击事件 我们需要使用不同于 android:onClick 的属性来避免冲突。
    
下面是一些用来避免冲突的属性：

|Class|Listener Setter|Attribute|
|:---:|:---:|:---:|
|SearchView|setOnSearchClickListener(View.OnClickListener)|android:onSearchClick|
|ZoomControls|setOnZoomInClickListener(View.OnClickListener)|android:onZoomIn|
|ZoomControls|setOnZoomOutClickListener(View.OnClickListener)|android:onZoomOut|

***

## 布局详情
本部分源码请参考 [DataBindingDemo][DataBindingDemo] -> `CombineActivity` 部分

### 导入(Imports)

+ data 标签内可以有多个 import 标签。你可以在布局文件中像使用 Java 一样导入引用

```xml
<data>
    <import type="android.view.View"/>
</data>

<TextView
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>
```

+ 当类名发生冲突时，可以使用 alias

```xml
<import type="android.view.View"/>
<import type="com.connorlin.databinding.ui.View" alias="AliasView"/>
```

+ 导入的类型也可以用于变量的类型引用和表达式中

```xml
<data>
    <import type="com.connorlin.databinding.model.User"/>
    <import type="java.util.List"/>
    <variable name="user" type="User"/>
    <variable name="userList" type="List<User>"/>
</data>
```

> 注意：Android Studio 还没有对导入提供自动补全的支持。你的应用还是可以被正常编译，要解决这个问题，你可以在变量定义中使用完整的包名。

+ 导入也可以用于在表达式中使用静态方法

```java
public class MyStringUtils {
    public static String capitalize(final String word) {
        if (word.length() > 1) {
            return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }
}
```

```xml
<data>
    <import type="com.connorlin.databinding.utils.MyStringUtils"/>
    <variable name="user" type="com.connorlin.databinding.model.User"/>
</data>
…
<TextView
   android:text="@{MyStringUtils.capitalize(user.lastName)}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```

+ java.lang.* 包中的类会被自动导入，可以直接使用，例如， 要定义一个 String 类型的变量

```xml
<variable name="test" type="String" />
```

### 变量 Variables
+ data 标签中可以有任意数量的 variable 标签。每个 variable 标签描述了会在 binding 表达式中使用的属性。

```xml
<data>
    <import type="android.graphics.drawable.Drawable"/>
    <variable name="user"  type="com.connorlin.databinding.model.User"/>
    <variable name="image" type="Drawable"/>
    <variable name="note"  type="String"/>
</data>
```

+ 可以在表达式中直接引用带 id 的 view，引用时采用驼峰命名法。

```xml
<TextView
    android:id="@+id/first_name"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@={user.firstName}" />

<TextView
    android:text="@{user.lastName}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="@{firstName.getVisibility() == View.GONE ? View.GONE : View.VISIBLE}" />
    <!-- 这里TextView直接引用第一次TextView，firstName为id 的驼峰命名 -->
```

+ binding 类会生成一个命名为 context 的特殊变量(其实就是 rootView 的 getContext() ) 的返回值)，这个变量可用于表达式中。 如果有名为 context 的变量存在，那么生成的这个 context 特殊变量将被覆盖。

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{handler.loadString(context)}"/>
```
```java
public String loadString(Context context) {
    // 使用生成的context变量
    return context.getResources().getString(R.string.string_from_context);
}
```

### 自定义绑定类名
默认情况下，binding 类的名称取决于布局文件的命名，以大写字母开头，移除下划线，后续字母大写并追加 “Binding” 结尾。这个类会被放置在 databinding 包中。举个例子，布局文件 contact_item.xml 会生成 ContactItemBinding 类。如果 module 包名为 com.example.my.app ，binding 类会被放在 com.example.my.app.databinding 中。

通过修改 data 标签中的 class 属性，可以修改 Binding 类的命名与位置。举个例子：

```xml
<data class="CustomBinding">
    ...
</data>
```

以上会在 databinding 包中生成名为 CustomBinding 的 binding 类。如果需要放置在不同的包下，可以在前面加 `“.”`：

```xml
<data class=".CustomBinding">
    ...
</data>
```

这样的话， CustomBinding 会直接生成在 module 包下。如果提供完整的包名，binding 类可以放置在任何包名中：

```xml
<data class="com.example.CustomBinding">
    ...
</data>
```

### Includes
在使用应用命名空间的布局中，变量可以传递到任何 `include` 布局中。

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">
   <data>
       <variable name="user" type="com.connorlin.databinding.model.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <include layout="@layout/include"
            app:user="@{user}"/>
   </LinearLayout>
</layout>
```

> 需要注意， activity_combine.xml 与 include.xml 中都需要声明 user 变量。

Data binding 不支持直接包含 `merge` 节点。举个例子， 以下的代码<font color = "red">**不能正常运行** </font>：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">
   <data>
       <variable name="user" type="com.connorlin.databinding.model.User"/>
   </data>
   <merge>
       <include layout="@layout/include"
            app:user="@{user}"/>
   </merge>
</layout>
```

### 表达式语言
#### 通用特性
表达式语言与 Java 表达式有很多相似之处。下面是相同之处：

+ 数学计算 + - / * %
+ 字符串连接 +
+ 逻辑 && ||
+ 二进制 & | ^
+ 一元 + - ! ~
+ 位移 >> >>> <<
+ 比较 == > < >= <=
+ instanceof
+ 组 ()
+ 字面量 - 字符，字符串，数字， null
+ 类型转换
+ 函数调用
+ 字段存取
+ 数组存取 []
+ 三元运算符 ?：

例子：

```xml
<!-- 内部使用字符串 & 字符拼接-->
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{`Age :` + String.valueOf(user.age)}"/>

<!-- 三目运算-->
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>
```
> 在xml中转义是不可避免的，如 :  使用“&&”是编译不通过的，需要使用转义字符 "&amp;&amp;"

**附：常用的转义字符**

|显示结果|描述|转义字符|十进制|
|:---:|:---:|:---:|:---:|
|&nbsp;|空格|&amp;nbsp;|&amp;#160;|
|&lt;|小于号|&amp;lt;|&amp;#60;|
|&gt;|大于号|&amp;gt;|&amp;#62;|
|&amp;|与号|&amp;amp;|&amp;#38;|
|&quot;|引号|&amp;quot;|&amp;#34;|
|'|撇号|&amp;apos; |&amp;#39;|
|&times;|乘号|&amp;times;|&amp;#215;|
|&divide;|除号|&amp;divide;|&amp;#247;|

#### 不支持的操作符
一些 Java 中的操作符在表达式语法中不能使用。

+ this
+ super
+ new
+ 显式泛型调用 `<T>`

#### Null合并运算符
Null合并运算符 `??` 会在非 null 的时候选择左边的操作，反之选择右边。

```
android:text="@{user.lastName ?? `Default LastName`}"
```

等同于

```
android:text="@{user.lastName != null ? user.lastName : `Default LastName`}"
```

#### 容器类
通用的容器类：数组，lists，sparse lists，和 maps，可以用 `[]` 操作符来存取

```xml
<data>
    <import type="android.util.SparseArray"/>
    <import type="java.util.Map"/>
    <import type="java.util.List"/>
    <variable name="list" type="List<String>"/>
    <variable name="sparse" type="SparseArray<String>"/>
    <variable name="map" type="Map<String, String>"/>
    <variable name="index" type="int"/>
    <variable name="key" type="String"/>
</data>
…
android:text="@{list[index]}"
…
android:text="@{sparse[index]}"
…
android:text="@{map[key]}"
```

#### 字符串常量
使用单引号把属性包起来，就可以很简单地在表达式中使用双引号：

```xml
android:text='@{map["firstName"]}'
```

也可以用双引号将属性包起来。这样的话，字符串常量就可以用 &quot; 或者反引号 ( ` ) 来调用

```xml
android:text="@{map[`firstName`}"
android:text="@{map[&quot;firstName&quot;]}"
```

#### 资源
也可以在表达式中使用普通的语法来引用资源：

```
android:text="@{@string/fullname(user.fullName)"
```

字符串格式化和复数形式可以这样实现：

```
android:text="@{@plurals/sample_plurals(num)}"
```

当复数形式有多个参数时，应该这样写：

```
android:text="@{@plurals/numbers(num, num)}"
```

一些资源需要显示类型调用。

|Type|Normal Reference|Expression Reference|
|:---|:---|:---|
|String[]|@array|@stringArray|
|int[]|@array|@intArray|
|TypedArray|@array|@typedArray|
|Animator |@animator |@animator|
|StateListAnimator|@animator |@stateListAnimator|
|color int|@color|@color|
|ColorStateList|@color|@colorStateList|

***

## 数据对象 (Data Objects)
任何 POJO 对象都能用在 Data Binding 中，但是更改 POJO 并不会同步更新 UI。Data Binding 的强大之处就在于它可以让你的数据拥有更新通知的能力。

有三种不同的动态更新数据的机制：

+ Observable 对象
+ Observable 字段
+ Observable 容器类

当以上的 observable 对象绑定在 UI 上，数据发生变化时，UI 就会同步更新。

### Observable 对象
当一个类实现了 Observable 接口时，Data Binding 会设置一个 listener 在绑定的对象上，以便监听对象字段的变动。

Observable 接口有一个添加/移除 listener 的机制，但通知取决于开发者。为了简化开发，Android 原生提供了一个基类 `BaseObservable` 来实现 listener 注册机制。这个类也实现了字段变动的通知，只需要在 getter 上使用 Bindable 注解，并在 setter 中通知更新即可。

```java
public class ObservableContact extends BaseObservable {
    private String mName;
    private String mPhone;

    public ObservableContact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
        notifyPropertyChanged(BR.phone);
    }
}
```

`BR` 是编译阶段生成的一个类，功能与 R.java 类似，用 @Bindable 标记过 getter 方法会在 BR 中生成一个 entry。
当数据发生变化时需要调用 `notifyPropertyChanged(BR.firstName)` 通知系统 `BR.firstName` 这个 entry 的数据已经发生变化以更新UI。

### ObservableFields
创建 Observable 类还是需要花费一点时间的，如果想要省时，或者数据类的字段很少的话，可以使用 `ObservableField` 以及它的派生 `ObservableBoolean`、
`ObservableByte` 、`ObservableChar `、`ObservableShort`、`ObservableInt`、`ObservableLong`、`ObservableFloat`、`ObservableDouble`、
`ObservableParcelable` 。 

ObservableFields 是包含 observable 对象的单一字段。原始版本避免了在存取过程中做打包/解包操作。要使用它，在数据类中创建一个 public final 字段：

```java
public class ObservableFieldContact {
    public ObservableField<String> mName = new ObservableField<>();
    public ObservableField<String> mPhone = new ObservableField<>();

    public ObservableFieldContact(String name, String phone) {
        mName.set(name);
        mPhone.set(phone);
    }
}
```

要存取数据，只需要使用 get() / set() 方法：

```java
mObservableFieldContact.mName.set("ConnorLin");
mObservableFieldContact.mPhone.set("12345678901");

String name = mObservableFieldContact.mName.get();
```

### Observable Collections 容器类
一些应用会使用更加灵活的结构来保持数据。Observable 容器类允许使用 key 来获取这类数据。当 key 是类似 String 的一类引用类型时，使用 ObservableArrayMap 会非常方便。

```java
ObservableArrayMap<String, String> mUser = new ObservableArrayMap<>();
mUser.put("firstName", "Connor");
mUser.put("lastName", "Lin");
mUser.put("age", "28");
mBinding.setUser(mUser);
```

在布局中，可以用 String key 来获取 map 中的数据：

```xml
<data>
    <import type="android.databinding.ObservableMap"/>
    <variable name="user" type="ObservableMap&lt;String, String>"/>
</data>
…
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text='@{user["firstName"]}'/>

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text='@{user["lastName"]}'/>

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text='@{user["age"]}'/>
```

当 key 是整数类型时，可以使用 ObservableArrayList ：

```java
ObservableArrayList<String> user = new ObservableArrayList<>();
user.add("Google");
user.add("Inc.");
user.add("17");
```

在布局文件中，使用下标获取列表数据：

```xml
<data>
    <import type="android.databinding.ObservableList"/>
    <variable name="user" type="ObservableList<String>"/>
</data>
…
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text='@{userList[0]}'/>

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text='@{userList[1]}'/>

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text='@{userList[2]}'/>
```

***

## 生成绑定
生成的 binding 类将布局中的 View 与变量绑定在一起。就像先前提到过的，类名和包名可以自定义 。生成的 binding 类会继承 ViewDataBinding 。

### Creating
binding 应该在 inflate 之后创建，确保 View 的层次结构不会在绑定前被干扰。绑定布局有好几种方式。最常见的是使用 binding 类中的静态方法。inflate 函数会 inflate View 并将 View 绑定到 binding 类上。此外有更加简单的函数，只需要一个 LayoutInflater 或一个 ViewGroup：

```java
MyLayoutBinding binding = MyLayoutBinding.inflate(layoutInflater);
MyLayoutBinding binding = MyLayoutBinding.inflate(layoutInflater, viewGroup, false);
```

如果布局使用不同的机制来 inflate，则可以独立做绑定操作：

```java
MyLayoutBinding binding = MyLayoutBinding.bind(viewRoot);
```

有时绑定关系是不能提前确定的。这种情况下，可以使用 DataBindingUtil ：

```java
ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater, layoutId, parent, attachToParent);
ViewDataBinding binding = DataBindingUtil.bindTo(viewRoot, layoutId);
```

### Views With IDs
布局中每一个带有 ID 的 View，都会生成一个 public final 字段。binding 过程会做一个简单的赋值，在 binding 类中保存对应 ID 的 View。这种机制相比调用 findViewById 效率更高。举个例子：

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="user" type="com.connorlin.databinding.model.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.firstName}"
           android:id="@+id/firstName"/>
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"
           android:id="@+id/lastName"/>
   </LinearLayout>
</layout>
```

将会在 binding 类内生成：

```java
public final TextView firstName;
public final TextView lastName;
```

ID 在 Data Binding 中并不是必需的，但是在某些情况下还是有必要对 View 进行操作。

### Variables
每一个变量会有相应的存取函数：

```xml
<data>
    <import type="android.graphics.drawable.Drawable"/>
    <variable name="user"  type="com.connorlin.databinding.model.User"/>
    <variable name="image" type="Drawable"/>
    <variable name="note"  type="String"/>
</data>
```

并在 binding 类中生成对应的 getters 和 setters：

```java
public com.connorlin.databinding.model.User getUser();
public void setUser(com.connorlin.databinding.model.User user);
public Drawable getImage();
public void setImage(Drawable image);
public String getNote();
public void setNote(String note);
```

### ViewStubs
本部分源码请参考 [DataBindingDemo][DataBindingDemo] -> `ViewStubActivity` 部分。

ViewStub 相比普通 View 有一些不同。ViewStub 一开始是不可见的，当它们被设置为可见，或者调用 inflate 方法时，ViewStub 会被替换成另外一个布局。

因为 ViewStub 实际上不存在于 View 结构中，binding 类中的类也得移除掉，以便系统回收。因为 binding 类中的 View 都是 final 的，所以Android 提供了一个叫 `ViewStubProxy` 的类来代替 ViewStub 。开发者可以使用它来操作 ViewStub，获取 ViewStub inflate 时得到的视图。

但 inflate 一个新的布局时，必须为新的布局创建一个 binding。因此， `ViewStubProxy` 必须监听 ViewStub 的 `ViewStub.OnInflateListener`，并及时建立 binding。由于 ViewStub 只能有一个 `OnInflateListener`，你可以将你自己的 listener 设置在 ViewStubProxy 上，在 binding 建立之后， listener 就会被触发。

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <LinearLayout ...>
        <ViewStub
            android:id="@+id/view_stub"
            android:layout="@layout/include"
            ... />
    </LinearLayout>
</layout>
```

在 Java 代码中获取 binding 实例，为 `ViewStubProy` 注册  `ViewStub.OnInflateListener` 事件：

```java
mActivityViewStubBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_stub);
mActivityViewStubBinding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
    @Override
    public void onInflate(ViewStub stub, View inflated) {
        IncludeBinding viewStubBinding = DataBindingUtil.bind(inflated);
        User user = new User("Connor", "Lin", 28);
        viewStubBinding.setUser(user);
    }
});
```

通过 `ViewStubProxy` 来 inflate ViewStub :

```java
public void inflate(View view) {
    if (!mActivityViewStubBinding.viewStub.isInflated()) {
        mActivityViewStubBinding.viewStub.getViewStub().inflate();
    }
}
```
> 此处 `isInflated()` 和 `getViewStub()` 会标红，请不要担心，这并不是错误，是 [ViewStubProxy](https://developer.android.com/reference/android/databinding/ViewStubProxy.html) 中的方法。

### 高级绑定
#### 动态变量
有时候，有一些不可知的 binding 类。例如，RecyclerView.Adapter 可以用来处理不同布局，这样的话它就不知道应该使用哪一个 binding 类。而在 onBindViewHolder(VH, int) ) 的时候，binding 类必须被赋值。

在这种情况下，RecyclerView 的布局内置了一个 item 变量。 BindingHolder 有一个 getBinding 方法，返回一个 ViewDataBinding 基类。

```java
public void onBindViewHolder(BindingHolder holder, int position) {
  holder.getBinding().setVariable(BR.item, mItemList.get(position));
  holder.getBinding().executePendingBindings();
}
```

以上，详细请参考 [DataBindingDemo][DataBindingDemo] -> `MainActivity` 部分(使用 `RecyclerView` 实现)。

#### 直接 binding
当变量或者 observable 发生变动时，会在下一帧触发 binding。有时候 binding 需要马上执行，这时候可以使用 `executePendingBindings()`。

#### 后台线程
只要数据不是容器类，你可以直接在后台线程做数据变动。Data binding 会将变量/字段转为局部量，避免同步问题。

***

## 属性设置
本部分源码请参考 [DataBindingDemo][DataBindingDemo] -> `AttributeSettersActivity` 部分。

当绑定数据发生变动时，生成的 binding 类必须根据 binding 表达式调用 View 的 setter 函数。Data binding 框架内置了几种自定义赋值的方法。

### 自动设置属性
对一个 attribute 来说，Data Binding 会尝试寻找对应的 setAttribute 函数。属性的命名空间不会对这个过程产生影响，只有属性的命名才是决定因素。

举个例子，针对一个与 TextView 的 android:text 绑定的表达式，Data Binding会自动寻找 setText(String) 函数。如果表达式返回值为 int 类型， Data Binding则会寻找 setText(int) 函数。所以需要小心处理函数的返回值类型，必要的时候使用强制类型转换。

需要注意的是，Data Binding 在对应名称的属性不存在的时候也能继续工作。你可以轻而易举地使用 Data Binding 为任何 setter “创建” 属性。
如 [DataBindingDemo][DataBindingDemo] 中的自定义布局 Card，并没有添加 `declare-styleable`，但是可以使用自动 setter 的特性来调用这些函数。

```xml
<com.connorlin.databinding.view.Card
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:object="@{user}"/>
```

### 重命名属性设置
一些属性的命名与 setter 不对应。针对这些函数，可以用 BindingMethods 注解来将属性与 setter 绑定在一起。举个例子， `android:tint` 属性可以这样与 `setImageTintList(ColorStateList) )` 绑定，而不是 setTint :

```java
@BindingMethods({
      @BindingMethod(type = "android.widget.ImageView",
                      attribute = "android:tint",
                      method = "setImageTintList"),
})
```

Android 框架中的 setter 重命名已经在库中实现了，我们只需要专注于自己的 setter。

### 自定义属性设置
一些属性需要自定义 setter 逻辑。例如，目前没有与 `android:paddingLeft` 对应的 setter，只有一个 `setPadding(left, top, right, bottom)` 函数。结合静态 binding adapter 函数与 BindingAdapter 注解可以让开发者自定义属性 setter。

Android 属性已经内置一些 BindingAdapter。例如，这是一个 paddingLeft 的自定义 setter：

```java
@BindingAdapter("android:paddingLeft")
public static void setPaddingLeft(View view, int padding) {
  view.setPadding(padding,
                  view.getPaddingTop(),
                  view.getPaddingRight(),
                  view.getPaddingBottom());
}
```

Binding adapter 在其他自定义类型上也很好用。举个例子，一个 loader 可以在非主线程加载图片。

当存在冲突时，开发者创建的 binding adapter 会覆盖 Data Binding 的默认 adapter。

你也可以创建多个参数的 adapter：

```java
// 无需手动调用此函数
@BindingAdapter({"imageUrl", "error"})
public static void loadImage(ImageView view, String url, Drawable error) {
    Glide.with(view.getContext()).load(url).error(error).into(view);
}
```

```xml
<!-- 当url存在时，会自动调用注解方法，即loadImage()-->
<ImageView 
    app:imageUrl=“@{url}”
    app:error=“@{@drawable/ic_launcher}”/>
```

当 imageUrl 与 error 存在时这个 adapter 会被调用。imageUrl 是一个 String，error 是一个 Drawable。

- 在匹配时自定义命名空间会被忽略
- 你可以为 android 命名空间编写 adapter

Binding adapter 方法可以获取旧的赋值。只需要将旧值放置在前，新值放置在后：

```java
@BindingAdapter("android:paddingLeft")
public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
  if (oldPadding != newPadding) {
      view.setPadding(newPadding,
                      view.getPaddingTop(),
                      view.getPaddingRight(),
                      view.getPaddingBottom());
  }
}
```

事件 handlers 仅可用于只拥有一个抽象方法的接口或者抽象类。例如：

```java
@BindingAdapter("android:onLayoutChange")
public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener oldValue,
      View.OnLayoutChangeListener newValue) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        if (oldValue != null) {
            view.removeOnLayoutChangeListener(oldValue);
        }
        if (newValue != null) {
            view.addOnLayoutChangeListener(newValue);
        }
    }
}
```

当 listener 内置多个函数时，必须分割成多个 listener。例如， View.OnAttachStateChangeListener 内置两个函数： `onViewAttachedToWindow()`与 `onViewDetachedFromWindow()` 。在这里必须为两个不同的属性创建不同的接口。

```java
@TargetApi(VERSION_CODES.HONEYCOMB_MR1)
public interface OnViewDetachedFromWindow {
    void onViewDetachedFromWindow(View v);
}

@TargetApi(VERSION_CODES.HONEYCOMB_MR1)
public interface OnViewAttachedToWindow {
    void onViewAttachedToWindow(View v);
}
```

因为改变一个 listener 会影响到另外一个，我们必须编写三个不同的 adapter，包括修改一个属性的和修改两个属性的。

```java
@BindingAdapter("android:onViewAttachedToWindow")
public static void setListener(View view, OnViewAttachedToWindow attached) {
    setListener(view, null, attached);
}

@BindingAdapter("android:onViewDetachedFromWindow")
public static void setListener(View view, OnViewDetachedFromWindow detached) {
    setListener(view, detached, null);
}

@BindingAdapter({"android:onViewDetachedFromWindow", "android:onViewAttachedToWindow"})
public static void setListener(View view, final OnViewDetachedFromWindow detach,
        final OnViewAttachedToWindow attach) {
    if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1) {
        final OnAttachStateChangeListener newListener;
        if (detach == null && attach == null) {
            newListener = null;
        } else {
            newListener = new OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    if (attach != null) {
                        attach.onViewAttachedToWindow(v);
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    if (detach != null) {
                        detach.onViewDetachedFromWindow(v);
                    }
                }
            };
        }
        final OnAttachStateChangeListener oldListener = ListenerUtil.trackListener(view,
                newListener, R.id.onAttachStateChangeListener);
        if (oldListener != null) {
            view.removeOnAttachStateChangeListener(oldListener);
        }
        if (newListener != null) {
            view.addOnAttachStateChangeListener(newListener);
        }
    }
}
```

上面的例子比普通情况下复杂，因为 View 是 `add/remove` `View.OnAttachStateChangeListener` 而不是 `set`。 `android.databinding.adapters.ListenerUtil`可以用来辅助跟踪旧的 listener 并移除它。

对应 `addOnAttachStateChangeListener(View.OnAttachStateChangeListener) ) `支持的 api 版本，
通过向 `OnViewDetachedFromWindow` 和 `OnViewAttachedToWindow` 添加 `@TargetApi(VERSION_CODES.HONEYCHOMB_MR1)` 注解，
Data Binding 代码生成器会知道这些 listener 只会在 Honeycomb MR1 或更新的设备上使用。

***

## 转换器Converters
### 对象转换
当 binding 表达式返回对象时，会选择一个 setter（自动 Setter，重命名 Setter，自定义 Setter），将返回对象强制转换成 setter 需要的类型。

下面是一个使用 ObservableMap 保存数据的例子：

```xml
<TextView
  android:text='@{userMap["lastName"]}'
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```

在这里， userMap 会返回 Object 类型的值，而返回值会被自动转换成 setText(CharSequence) 需要的类型。当对参数类型存在疑惑时，开发者需要手动做类型转换。

### 自定义转换
有时候会自动在特定类型直接做类型转换。例如，当设置背景的时候：

```xml
<View
  android:background="@{isError.get() ? @color/colorAccent : @color/colorPrimary}"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```

在这里，背景需要的是 Drawable ，但是 color 是一个整数。当需要 Drawable 却返回了一个整数时， int 会自动转换成 ColorDrawable 。这个转换是在一个 BindingConversation 注解的静态函数中实现：

```java
@BindingConversion
public static ColorDrawable convertColorToDrawable(int color) { 
    return new ColorDrawable(color); 
}
```

需要注意的是，这个转换只能在 setter 阶段生效，所以 不允许 混合类型：

```xml
<View
  android:background="@{isError.get() ? @drawable/error : @color/colorPrimary}"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```

***

## Android Studio对Data Binding的支持

* Android Studio 支持 Data Binding 表现为：

    * 语法高亮
    * 标记表达式语法错误
    * XML 代码补全
    * 跳转到声明或快速文档 
    
> 注意：数组和泛型类型，如 Observable 类，当没有错误时可能会显示错误。

* 在预览窗口可显示 Data Binding 表达式的默认值。例如：

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{user.firstName, default=FirstName}"/>
  <!-- TextView 的 text 默认值为 FirstName -->
```

如果你需要在设计阶段显示默认值，你可以使用 `tools` 属性代替默认值表达式，详见 [设计阶段布局属性 ](http://tools.android.com/tips/layout-designtime-attributes)

[DataBindingDemo]: https://github.com/ConnorLin/DataBindingDemo

## 参考资料
1. [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html)
2. [安卓 Data Binding 使用方法总结](http://blog.csdn.net/zhaizu/article/details/51491455)
3. [(译)Data Binding 指南](http://yanghui.name/blog/2016/02/17/data-binding-guide/?utm_source=tuicool&utm_medium=referral)
4. [精通 Android Data Binding](https://github.com/LyndonChin/MasteringAndroidDataBinding/blob/master/README.md)

***

**我的简书账号是 ConnorLin，欢迎关注！**

**我的简书专题是 Android开发技术分享，欢迎关注！**

**[我的个人博客](http://connorlin.github.io/) 欢迎关注！**

**原创文章，欢迎转载，转载请注明出处!**

![](https://raw.githubusercontent.com/ConnorLin/BlogImages/master/wechat-qcode.jpg)

**欢迎您扫一扫上面的微信公众号，订阅我的博客！**
