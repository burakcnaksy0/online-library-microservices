# Folksdev Microservices Project - Bilgi ve Gelişim Notları

Bu proje, mikroservis mimarisi standartlarını öğrenmek ve uygulamak amacıyla geliştirilmektedir. Aşağıda, sistemde kullanılan temel teknolojiler, hata yönetimi stratejileri ve yapılan optimizasyonlar detaylıca açıklanmıştır.

## 1. Servis Kaydı ve Keşfi (Service Discovery) - Eureka Server

Mikroservis mimarisinde her servisin (Book Service, Library Service vb.) IP adresi ve port numarası dinamik olarak değişebilir. Servislerin birbirlerini bulabilmesi için bir "telefon rehberi"ne ihtiyaç vardır.

*   **Eureka Server:** Projenin merkezi kayıt sunucusudur.
*   **Çalışma Mantığı:** Her mikroservis ayağa kalktığında Eureka'ya giderek "Ben X servisiyim, IP adresim ve portum budur" diyerek kendini kaydeder.
*   **Faydası:** Servisler birbirlerine istek atarken `localhost:8080` gibi sabit adresler yerine direkt servis isimlerini (`book-service`) kullanabilirler.

## 2. Mikroservisler Arası İletişim - OpenFeign

Servislerin birbirleriyle HTTP üzerinden haberleşmesini sağlayan deklaratif bir REST istemcisidir.

*   **Neden Feign?** Standart `RestTemplate` kullanımındaki karmaşık kod yapısını ortadan kaldırır. Sadece bir Interface (Arayüz) tanımlayarak ve üzerine `@FeignClient` ekleyerek karşı servise istek atılabilir.
*   **Projedeki Kullanımı:** `BookServiceClient` üzerinden `book-service`'e ait kitap çekme işlemleri yönetilir.

## 3. Hata Yönetimi (Error Handling) - Custom ErrorDecoder

Feign Client üzerinden atılan isteklerde, karşı servisten gelen HTTP hatalarını (4xx, 5xx) anlamlandırmak için özelleştirilmiş bir yapı kullanılmıştır.

*   **`RetrieveMessageErrorDecoder`:** 
    *   Karşı servisten gelen HTTP yanıt kodlarını yakalar.
    *   Yanıtın body (gövde) kısmındaki JSON verisini `IOUtils` ile okuyarak anlamlı metne dönüştürür.
    *   Örneğin; `book-service` 404 (Not Found) gönderdiğinde, bu sınıf bunu yakalayıp projenin kendi istisnası olan `BookNotFoundException`'ı fırlatır.
    *   Bu aşamada **Null-Safe** (boş veri güvenliği) kontrolleri eklenerek, sunucudan `Date` başlığı gelmese bile sistemin çökmemesi sağlanmıştır.

## 4. Hata Toleransı ve Dayanıklılık (Fault Tolerance) - Resilience4j

Sistemdeki bir servis kapandığında veya yavaşladığında tüm sistemin çökmesini engellemek için kullanılan "sigorta" mekanizmasıdır.

*   **Circuit Breaker (Devre Kesici):** İsteklerin sürekli hata alması durumunda devreyi "açık" konuma getirerek karşı servise gitmeyi durdurur ve sistemi korur.
*   **Fallback (Geri Dönüş):** Eğer bir çağrı başarısız olursa, kullanıcıya hata sayfası yerine "varsayılan (default)" bazı veriler dönülmesini sağlar.

### Yapılan Optimizasyonlar:

1.  **Fallback Factory Kullanımı:** Eskiden Feign arayüzü içinde `default` metotlarla yazılan fallback mantığı, `BookServiceClientFallbackFactory` sınıfına taşınarak mimari daha temiz ve yönetilebilir hale getirildi.
2.  **İş Hatası (Business Error) Ayrımı:** `BookNotFoundException` (404) gibi hataların aslında servisin çökmesiyle değil, sadece yanlış bilgi girilmesiyle ilgili olduğu tanımlandı. `application.properties` üzerinden bu istisnaların Circuit Breaker'ı tetiklememesi sağlandı (`ignore-exceptions`).
3.  **Hata Loglama:** Fallback metotlarına Logger eklenerek, sistemin neden "default" veri döndüğü (zaman aşımı mı? bağlantı hatası mı?) konsol üzerinden izlenebilir hale getirildi.
4.  **Hata Fırlatma Şartı:** Fallback mekanizması içine "eğer hata gerçekten 'kitap bulunamadı' ise default veri dönme, direkt hatayı kullanıcıya göster" mantığı eklendi.

## 5. API Gateway - Merkezi İstek Yönetimi

Mikroservislere gelen tüm isteklerin tek bir noktadan (tek bir port üzerinden) karşılanıp ilgili servislere yönlendirilmesini (routing) sağlayan sistemdir.

*   **Spring Cloud Gateway Server MVC:** Projede, Spring WebFlux (Reactive) yerine Spring WebMVC tabanlı Gateway kullanılmıştır.
*   **Java Tabanlı Yönlendirme:** Bu sürümde YAML destekli yönlendirme stabil çalışmadığı için, `RouterFunction` ve `GatewayConfig.java` kullanılarak Java tabanlı route konfigürasyonu gerçekleştirilmiştir.
*   **Lb:// Protokolü:** `LoadBalancerFilterFunctions.lb("library-service")` kullanılarak, sabit IP'ler yerine Eureka'dan alınan dinamik adreslere yük dengeli (load balanced) istek atılır.

## 6. Dağıtık İzleme (Distributed Tracing) - Micrometer & Zipkin

Farklı mikroservisler arasında dolaşan tek bir isteğin yolculuğunu izlemek, darboğazları (bottlenecks) ve hataları bulmak için kullanılır.

*   **Micrometer Tracing & Brave:** Spring Boot 3+ (ve 4.x) sürümlerinde eski Sleuth eklentisi kaldırılarak yerine Micrometer Tracing getirilmiştir.
*   **Zipkin Server:** İsteğin `gateway -> library-service -> book-service` şeklinde akarken ne kadar süre harcadığını görselleştirmek için kullanılmıştır. (`http://localhost:9411`)
*   **Actuator Entegrasyonu:** `spring-boot-starter-actuator` özelliğiyle birlikte `management.tracing.sampling.probability=1.0` yapılandırması sayesinde tüm trafik (%100) Zipkin üzerine başarıyla aktarılmaktadır.

---
*Her yeni geliştirme ve optimizasyonda bu doküman güncellenecektir.*
