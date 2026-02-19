# 🚀 TitanHammer Pro - Quick Start Guide

## البناء السريع (1-2 دقيقة فقط!)

### 🖥️ على Windows:

```cmd
1. فك الضغط TitanHammerPro_COMPLETE_v1.0.0.zip
2. Double-click على BUILD_JAR.bat
3. انتظر دقيقة واحدة
4. خذ الملف من: target\TitanHammerPro-1.0.0.jar
```

### 🐧 على Linux/Mac:

```bash
# 1. فك الضغط
unzip TitanHammerPro_COMPLETE_v1.0.0.zip
cd TitanHammerPro/

# 2. شغّل السكريبت
chmod +x BUILD_JAR.sh
./BUILD_JAR.sh

# 3. خذ الملف
# target/TitanHammerPro-1.0.0.jar
```

### 💻 على VPS/Server:

```bash
# إذا ما عندك Maven:
apt install maven -y  # Ubuntu/Debian
yum install maven -y  # CentOS/RHEL

# بناء البلجن:
cd TitanHammerPro/
mvn clean package

# الملف جاهز في:
# target/TitanHammerPro-1.0.0.jar
```

### 🎨 باستخدام IntelliJ IDEA:

```
1. Open Project → اختر مجلد TitanHammerPro
2. انتظر Maven يحمل dependencies (أول مرة فقط)
3. Maven (يمين الشاشة) → Lifecycle → double-click على "package"
4. الملف في: target/TitanHammerPro-1.0.0.jar
```

## 📦 التثبيت:

```
1. خذ TitanHammerPro-1.0.0.jar
2. حطه في مجلد plugins/ في سيرفرك
3. أعد تشغيل السيرفر
4. البلجن يشتغل تلقائياً!
```

## ⚡ استخدام سريع:

```
/th give <player>  - أعطي المطرقة
Shift + Right Click - افتح القائمة
```

## 🔧 المتطلبات:

✅ Paper 1.21+ (أو Purpur)
✅ Java 21+
⚠️ Maven (للبناء فقط - مرة واحدة)

## ❓ مشاكل شائعة:

**Maven not found?**
```bash
# Ubuntu/Debian
sudo apt install maven

# Windows
Download from: https://maven.apache.org/download.cgi
```

**Java version error?**
```
Download Java 21 from: https://adoptium.net/
```

**Build failed?**
```bash
# نظف وجرب مرة ثانية
mvn clean
mvn package
```

## 📞 الدعم:

إذا واجهت أي مشكلة، تواصل مع:
- **Developer:** Blooddev
- **Company:** PrimeHost
- **Website:** https://primehost.iq

---

**وقت البناء:** 1-2 دقيقة فقط! ⚡
**حجم الملف:** ~5 MB
**جاهز للاستخدام مباشرة!** ✅
