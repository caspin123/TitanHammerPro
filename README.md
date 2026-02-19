# 🔨 TitanHammer Pro - Professional Minecraft Plugin

<div dir="rtl">

## نظرة عامة 📖

TitanHammer Pro هو بلجن متقدم واحترافي لـ Minecraft Paper 1.21 يضيف فأس نذريت مخصص قوي مع نظام مستويات، شجرة مهارات، ترقيات، وأنظمة فلترة متقدمة.

### المميزات الرئيسية ✨

#### 🎯 نظام المستويات والخبرة
- نظام XP متقدم مع حفظ التقدم لكل لاعب (UUID-based)
- كسب الخبرة من تحطيم البلوكات وقتل الوحوش
- نقاط مهارة عند كل ارتقاء مستوى
- صيغة XP ديناميكية متزايدة

#### 🌳 شجرة المهارات
- 6 مهارات رئيسية قابلة للترقية
- نظام متطلبات مسبقة
- مكافآت قوية لكل مهارة
- واجهة GUI سهلة الاستخدام

#### ⚙️ نظام الترقيات
- **الضرر**: +15% لكل مستوى (10 مستويات كحد أقصى)
- **الكفاءة**: +2 مستوى كفاءة لكل ترقية (10 مستويات)
- **الحظ (Fortune)**: +1 حظ لكل مستوى (5 مستويات)
- **المتانة**: +10% متانة لكل مستوى (10 مستويات)
- **المدى**: توسيع نطاق التحطيم (3x3 → 5x5 → 7x7 → 9x9 → 11x11)

#### 🎛️ نظام فلتر البلوكات
- **وضع الجمع التلقائي**: البلوكات المحددة تذهب مباشرة للانفنتري
- **وضع الحذف التلقائي**: البلوكات المحددة تُحذف بدون drops
- تخصيص كامل للبلوكات المفلترة
- مجموعات فلتر جاهزة (خامات، حجارة، تراب، إلخ)

#### 💰 دعم أنظمة الاقتصاد
- Vault (المال)
- PlayerPoints (النقاط)
- Custom Economy (عملة مخصصة)
- دعم اقتصادات متعددة في نفس الوقت

#### 👹 دعم MythicMobs
- +25% ضرر على MythicMobs
- 1.5x مضاعف الـ drops
- XP مضاعف من قتل MythicMobs

#### 🛡️ حماية ضد الاستغلال
- حماية Silk Touch (منع استخدام الفأس مع Silk Touch)
- حماية ضد التكرار (Anti-Duplication)
- احترام أنظمة الحماية:
  - WorldGuard
  - Residence
  - Towny
  - GriefPrevention

#### ✨ تأثيرات بصرية وصوتية
- جزيئات عند تحطيم البلوكات
- أصوات مخصصة
- تأثيرات عند الارتقاء
- قابل للتخصيص بالكامل

#### 🌍 دعم متعدد اللغات
- الإنجليزية (English)
- العربية (Arabic)
- سهولة إضافة لغات جديدة

### التثبيت 📦

#### المتطلبات
- Paper/Purpur 1.21+
- Java 21+
- (اختياري) Vault
- (اختياري) PlayerPoints
- (اختياري) MythicMobs

#### خطوات التثبيت

1. **قم بتحميل الملف**
   ```
   ضع ملف TitanHammerPro.jar في مجلد plugins/
   ```

2. **أعد تشغيل السيرفر**
   ```
   /restart or /reload
   ```

3. **قم بتخصيص الإعدادات**
   ```
   plugins/TitanHammerPro/config.yml
   plugins/TitanHammerPro/messages_ar.yml
   plugins/TitanHammerPro/messages_en.yml
   ```

4. **أعط نفسك المطرقة**
   ```
   /titanhammer give <player> [level]
   ```

### الأوامر 📝

| الأمر | الوصف | الصلاحية |
|-------|-------|---------|
| `/titanhammer` | القائمة الرئيسية | `titanhammer.use` |
| `/th give <player> [level]` | إعطاء مطرقة لاعب | `titanhammer.give` |
| `/th reload` | إعادة تحميل الإعدادات | `titanhammer.reload` |
| `/th stats [player]` | عرض الإحصائيات | `titanhammer.use` |
| `/th help` | قائمة المساعدة | `titanhammer.use` |

### الصلاحيات 🔐

```yaml
titanhammer.*              # جميع الصلاحيات
titanhammer.use            # استخدام المطرقة
titanhammer.admin          # صلاحيات الإدارة
titanhammer.give           # إعطاء المطرقة للاعبين
titanhammer.reload         # إعادة التحميل
```

### الاستخدام 🎮

#### فتح القائمة الرئيسية
```
Shift + Right Click بينما تحمل المطرقة
```

#### التحطيم بمدى واسع
```
قم بتحطيم بلوك بشكل عادي (بدون Shift) وسيتم تحطيم البلوكات المحيطة
```

#### فلترة البلوكات
1. افتح القائمة الرئيسية
2. اضغط على "Block Filter"
3. اختر الوضع (Auto Collect / Auto Delete)
4. أضف البلوكات التي تريد فلترتها

### هيكل قاعدة البيانات 💾

البلجن يدعم SQLite و MySQL:

**SQLite** (افتراضي)
```
plugins/TitanHammerPro/data.db
```

**MySQL**
```yaml
database:
  type: MYSQL
  mysql:
    host: localhost
    port: 3306
    database: titanhammer
    username: root
    password: password
```

### API للمطورين 👨‍💻

```java
// الحصول على instance
TitanHammerPro plugin = TitanHammerPro.getInstance();

// الحصول على بيانات اللاعب
PlayerData playerData = plugin.getPlayerDataManager()
    .getPlayerData(player.getUniqueId());

// إضافة XP
playerData.addExperience(100.0);

// ترقية مهارة
if (playerData.spendSkillPoints(1)) {
    playerData.upgradeLevel("damage");
}

// التحقق من المطرقة
ItemStack item = player.getInventory().getItemInMainHand();
if (ItemUtil.isTitanHammer(item)) {
    // Do something
}
```

### التخصيص ⚙️

#### تعديل CustomModelData
```yaml
# config.yml
titan-hammer:
  custom-model-data: 2025  # غير هذا الرقم
```

#### تعديل المكافآت
```yaml
# config.yml
upgrades:
  damage:
    max-level: 10
    bonus-per-level: 15  # 15% per level
```

#### إضافة مهارات جديدة
```yaml
# config.yml
skill-tree:
  skills:
    my_custom_skill:
      name: "&a&lMy Skill"
      max-level: 5
      cost-per-level: 2
      benefits:
        - "&a+10% Something"
```

### الأداء 📊

- استخدام HikariCP لـ connection pooling
- عمليات قاعدة البيانات async
- نظام cache للبيانات
- تنظيف ذاكري تلقائي
- محمي ضد memory leaks

### الدعم الفني 🆘

إذا واجهت أي مشاكل:

1. تحقق من console للأخطاء
2. تأكد من استخدام Paper 1.21+
3. تأكد من Java 21+
4. راجع ملف الـ config
5. تواصل مع Blooddev

### Credits 👏

- **المطور**: Blooddev
- **الشركة**: PrimeHost
- **الموقع**: https://primehost.iq
- **Discord**: قريباً

### License 📄

هذا البلجن مطور خصيصاً لـ PrimeHost.
جميع الحقوق محفوظة © 2025 Blooddev

---

</div>

## English Overview

A professional Minecraft plugin for Paper 1.21 that adds a powerful custom Netherite Axe with leveling system, skill tree, upgrades, and advanced filtering systems.

### Key Features
✅ Advanced XP & Level System  
✅ Skill Tree with 6 Skills  
✅ 5 Upgrade Types (Damage, Efficiency, Fortune, Durability, Range)  
✅ Block Filter (Auto Collect / Auto Delete)  
✅ Multi-Economy Support (Vault, PlayerPoints, Custom)  
✅ MythicMobs Integration  
✅ Anti-Duplication Protection  
✅ Particle & Sound Effects  
✅ Bilingual (English & Arabic)  
✅ MySQL & SQLite Support  
✅ Fully Configurable  
✅ OOP Structure  

### Installation
1. Download TitanHammerPro.jar
2. Place in plugins/ folder
3. Restart server
4. Configure files in plugins/TitanHammerPro/
5. Give yourself the hammer: `/th give <player>`

### Commands
- `/titanhammer` - Main menu
- `/th give <player>` - Give hammer
- `/th reload` - Reload config
- `/th stats` - View statistics

### Usage
- **Shift + Right Click** to open GUI
- Break blocks normally for range mining
- Configure filters in Block Filter menu

---

**Developed by Blooddev for PrimeHost**  
**© 2025 All Rights Reserved**
