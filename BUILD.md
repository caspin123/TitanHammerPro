# 🏗️ TitanHammer Pro - Build Guide

## كيفية بناء البلجن من السورس كود

### المتطلبات
- Java 21 JDK
- Maven 3.9+
- Git (اختياري)

### خطوات البناء

#### الطريقة 1: باستخدام Maven

```bash
# 1. انتقل لمجلد المشروع
cd TitanHammerPro/

# 2. نظف المشروع
mvn clean

# 3. اجمع البلجن
mvn package

# 4. الملف النهائي موجود في:
# target/TitanHammerPro-1.0.0.jar
```

#### الطريقة 2: باستخدام IDE (IntelliJ IDEA)

1. افتح IntelliJ IDEA
2. File → Open → اختر مجلد TitanHammerPro
3. انتظر Maven يحمل الـ dependencies
4. اضغط على Maven tab (يمين الشاشة)
5. TitanHammerPro → Lifecycle → package
6. الملف النهائي في: `target/TitanHammerPro-1.0.0.jar`

#### الطريقة 3: باستخدام Eclipse

1. افتح Eclipse
2. File → Import → Maven → Existing Maven Projects
3. اختر مجلد TitanHammerPro
4. Right-click على المشروع → Run As → Maven build
5. Goals: `clean package`
6. Run

### حل المشاكل الشائعة

#### مشكلة: Maven لا يجد dependencies

```bash
# احذف مجلد .m2 وأعد التحميل
rm -rf ~/.m2/repository
mvn clean install
```

#### مشكلة: Java version خاطئ

```bash
# تحقق من نسخة Java
java -version

# يجب أن تكون 21 أو أحدث
# إذا لم تكن، حمّل Java 21 من:
# https://adoptium.net/
```

#### مشكلة: Build يفشل

```bash
# جرب build بدون tests
mvn clean package -DskipTests
```

### هيكل المشروع

```
TitanHammerPro/
├── pom.xml                          # Maven configuration
├── README.md                        # Documentation
├── BUILD.md                         # This file
├── src/
│   └── main/
│       ├── java/
│       │   └── dev/blooddev/titanhammer/
│       │       ├── TitanHammerPro.java           # Main class
│       │       ├── commands/                      # Commands
│       │       ├── economy/                       # Economy handlers
│       │       ├── gui/                          # GUI menus
│       │       ├── handlers/                     # Feature handlers
│       │       ├── listeners/                    # Event listeners
│       │       ├── managers/                     # Manager classes
│       │       ├── models/                       # Data models
│       │       └── utils/                        # Utility classes
│       └── resources/
│           ├── plugin.yml                        # Plugin info
│           ├── config.yml                        # Main config
│           ├── messages_en.yml                   # English messages
│           └── messages_ar.yml                   # Arabic messages
└── target/                          # Build output (generated)
    └── TitanHammerPro-1.0.0.jar    # Final JAR file
```

### Development Setup

#### إعداد بيئة التطوير

```bash
# 1. Clone المشروع (إذا على Git)
git clone <repository-url>
cd TitanHammerPro

# 2. Import في IDE المفضل لديك
# IntelliJ IDEA مفضل للتطوير

# 3. تثبيت dependencies
mvn clean install

# 4. Run test server
# استخدم Paper test server في:
# server-test/
```

#### Hot Reload للتطوير

أضف هذا لـ plugin.yml للتطوير:

```yaml
# For development only
load: STARTUP
```

ثم استخدم:
```bash
/reload confirm
```

### نصائح للمطورين

1. **استخدم Lombok** لتقليل boilerplate code
2. **اتبع OOP principles** للكود المنظم
3. **استخدم async operations** لقاعدة البيانات
4. **أضف comments** للكود المعقد
5. **اختبر التغييرات** قبل الـ commit

### المساهمة

إذا تبي تساهم في البلجن:

1. Fork المشروع
2. أنشئ branch جديد
3. اعمل التغييرات
4. اختبر التغييرات
5. أرسل Pull Request

---

**Happy Coding! 🚀**
