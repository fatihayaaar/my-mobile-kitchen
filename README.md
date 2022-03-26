# my-mobile-kitchen

Mobil mutfağım adlı projemizi geliştirirken belirli mimarilerden 
yararlanacağız. Aşama asama uygulamayı geliştireceği. Uygulama katmanlı mimari 
olarak, SOLID prensiplerine uygun ve tasarım desenlerinden faydalanarak 
geliştirecektir. İlk aşamalarda uygulamamızın ara yüzünü yapacağız ardından katmanlı
mimarinin katmanlarını tek tek yazacağız.


# Katmanlı Mimari
Projemiz JAVA programlama dili ile yazılacaktır. Proje veriler odaklı olduğu 
için katmanlı mimari kaçınılmaz oldu. Projemizde veri erişim katmanı, iş katmanı ve 
sunum katmanı olarak 3 katmanımız bulunacak.
# Veri Erişim Katmanı aşaması
Projemizin DAL(data acces layer – veri erişim katmanı) katmanıdır. Bu katman 
direk olarak verilerimize erişecek olan katmandır. Bu katman direkt olarak veri 
tabanına bağlanacak ve CRUD işlemlerini yapacaktır. Bu katmanda abstract repository 
pattern ve unit of work pattern tasarım desenini kullanacağız. Bunun yanında 
dependency injection, factory method, singleton ve facade tasarım desenleri de her 
katmanda olduğu gibi bu katmanda da kullanılacak. Bu katmanda her tabloya ait bir 
entity nesnesi ve her ana entity’e karşılık bir repository sınıfı olacak. Repository 
sınıfları da database helper sınıfından CRUD işlemleri yapacak ve bu database helper 
sınıfı yapılan CRUD işlemlerini sıraya alacak ve yazılımcı saveChanges() dediğinde 
sıraya alınmış tüm işlemleri veri tabanına excute edecek şekilde tasarlanıp yazılacaktır. 
Bu repository’ler unit of work adında bir sınıftan kullanılacaktır.


![232](https://user-images.githubusercontent.com/18555532/160259880-d23fe557-0089-4aed-94c7-a0ce0341f4d5.png)


Entity yani veri tabanı varlık nesneleri veri tabanında bir tabloya denk gelecek 
şekilde tasarlanacak. Entity nesneleri BaseEntity’e bağlı olacak şekilde tasarlanacak 
yani kalıtım alacaktır. Bu strategy tasarım deseni olarak adlandırılır. Ayrıca entity’ler 
arasında facade yazılım tasarımı ilişkisi de olacak, nedeni ise uygulamamızın veri 
tabanında çoka çok ilişkiler olduğu için ara tablolar kullanılacak ve tek hücrede birden 
çok veri tutmak istediğimiz için bu lazım olacak. Biz veri tabanında Images Adlı bir 
tablo oluşturduk bu tablonun tekil anahtarı hariç FoodID ve ImageID adında adın da 
iki alanını oluşturduk bu tablo sayesinde her yemeğin birden fazla resmi olmasını 
sağladık. İşte bu tablo için bir entity oluşturduk ama bu foodEntity sınıfının al sınıfı 
olarak işlem görüyor. Bu alt sınıfı da repository sınıfını yaptık ama iş katmanına 
geldiğimizde bu sınıf için ayrı bir iş sınıfı üretmeyeceğiz.
Repository sınıflarımız her entity nesnelerinden verileri alıp database helper 
sınıfına CRUD işlemlerini yaptıran sınıflardır. Bu sınıflarda da strategy tasarım deseni 
kullanılmıştır. Repository sınıfları factory method tasarım deseni ile üretilmektedir ve 
hepsi birer singleton örneğidir. Repository nesneleri RepositoryContainer adlı sınıftan 
getRepository() metodu ile üretilmektedir.
Repository’ler database helper sınıfına bağlanır ve veri tabanında ekleme, 
silme, güncelleme ve listeleme işlerini yapar. Database helper sınıflarında bu işlemleri 
sıraya alır ve Repository’de ki save metodu çalıştırıldığında bu repository ile ilgili tüm 
veri tabanı işlemlerini excute eder.
Unit of work sınıfımız repository’leri kullanır, içinde onları factory metod ile
dahil eder yazılımcı repository’leri tek unit of work nesnesi ile çağırır işlemleri yaptırır 
hepsinin işlemleri veri tabanına excute edilmeden önce sıraya alınır. En son tüm 
işlemler sıraya alındıktan sonra saveChanges() metodu ile tüm repository’lerde ki
sıraya alınmış işlemleri veri tabanına excute eder. Unit of work tasarım deseninin 
kullanılma sebebi de budur, ekleme silme güncelleme işlemleri beraber yapılmak 
isteniyorsa bu tasarım kalıbı ile sıraya alınır ve işlem bittiğinde bunları veri tabanına 
excute eder.
Veri erişim katmanımızda ki database helper sınıfımız cihazımız için sqlite veri 
tabanını oluşturur. Ve CRUD işlemlerini yapar. Ayrıca ContentValues alanı ile veri 
tabanı işlemlerini sıraya alır.
Veri erişim katmanımızda sadece sqlite veri tabanı işlemleri yapılmayacak. 
XML’den veri çekme işlemi de veri erişim katmanı üzerinden yapılacak.


# İş Katmanı aşaması
İş katmanı veri erişim katmanın ile sunum katmanı arasında köprü katmandır, 
iş katmanı veri erişim katmanından verileri alır belli iş kurallarına sokar sunum 
katmanına gönderir ya da sunum katmanından alıp veri erişim katmanına gönderir 
bizim projemizde de durum aynı işliyor Sadece ana entity’ler için Manager sınıfları 
oluşturduk. Her manager repository’lere bağlanıyor repository’lerin işlemleri sıraya 
sokmasını sağlıyor. Ve belirli kurallar getiriyor mesela değerler boş olamaz.
İş katmanımızda da unit of work benzeri bir yapı kullandık. Manager sınıfları 
DataProcessingFactory adlı sınıf tarafından yönetiliyor. Bu sınıfta ki saveChanges() 
metodu ile veri erişim katmanında ki unit of work sınıfının saveChanges() metodunu 
tetikler

![unknown](https://user-images.githubusercontent.com/18555532/160259933-dea4bb2e-106b-4f77-b0c2-dfb8da486ef5.png)


# Ara yüz Tasarım Aşaması
Ara yüz tasarımı minimal tasarım şeklinde yapılacaktır. Bir ana penceremiz 
olacak, o pencereden diğer pencere gidecek olan bir panel olacak. Ana pencereden 
gidilecek olan diğer pencereler yemekler, sanal stok, hakkımızda, yemek sihirbazı, 
tariflerim ve hesabım penceresi olacaktır. Yemekler penceresinde ise yemeklerin 
eklendiği ile yemeklerin listelendiği iki adet sekme olacak. Bu pencerelerin önce kağıt 
üstünde tasarımları belirlenecek daha sonra Android Studio ortamında oluşturulacak. 


![unknown](https://user-images.githubusercontent.com/18555532/160259685-2fcfec96-38cb-4d9f-9c37-833e5c840e5c.png)

![unknown2](https://user-images.githubusercontent.com/18555532/160259756-12218e17-ab3e-41ef-8039-88232ffcd467.png)

![4](https://user-images.githubusercontent.com/18555532/160259758-73d25833-8800-46de-ac95-cc1bd9aeea75.png)

![3](https://user-images.githubusercontent.com/18555532/160259759-fe6530c5-8979-428c-a720-3248d1494286.png)
