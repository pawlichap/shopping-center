# shopping-center
Zadanie rekrutacyjne na stanowisko JAVA developer


THE SHOPPING-CENTER

Project Shopping center to dwie małe aplikacje: shopping-registry i simple-store. 

shopping-registry otrzymuje powiadomienia od simple-store przy udanych zakupach. Posiada jeden endpoint, który loguje i rejestruje zdarzenie zakupu.

Simple-store - to aplikacja z 3 domenami: 
•	Customer - pozwala na tworzenie i wyszukiwanie klientów 
•	Item  - odpowiada za tworzenie i wyszukiwanie przedmiotów,
•	Store - realizuje zakup przedmiotów przez klienta po odpowiednich walidacjach.

Simple-store posiada bazę danych oraz zewnętrzny serwis (shopping-registry). W bazie danych przechowywani są klienci oraz przedmioty/towary.

W ramach projektu przygotowano testy jednostkowe oraz integracyjne ale ze wzgłedu na braki w implementacji kończą się niepowodzeniem.


ZADANIA

1. Przejrzeć wszystkie komentarze TODO i zaimplementować brakujące funkcjonalności zgodnie z opisem. Funkcjonalności można uznać za zaimplementowane, gdy testy zakończą się sukcesem w 100%.

2. Dodać funkcjonalności ekwipunktu klienta tzn. gdy klient zakupi przedmioty powinny pojawić się w jego ekwipunku (należy w tym celu rozszerzyć model oraz napisać do tej funkcjonalności nowe testy).

3. W klasie pl.ca.recruitment.simplestore.items.domain.ItemQuery zaimplementowano szukanie przedmiotów z filtrowaniem. Funkcjonalność napisana jest w nieskalowalny sposób, tzn. gdyby filtrów było więcej, drastycznie wzrósłby poziom skomplikowania kodu.
Należy zaproponować lepsze rozwiazanie, wspierające obsługę wielu filtrów, warunków logicznych OR/AND na kilku filtrach.

4. W aplikacji jest kilka poważnych błędów funkcjonalnych – należy je znaleźć i poprawić.

5. Aktualnie testy integracyjne i jednostkowe uruchamiane są podczas procesu budowania bez żadnych dodatkowych parametrów - należy umożliwić uruchamianie ich osobno.

6. Należy zaimplementować funkcjonalność udostępniajaca dokumentację API, generowaną automatycznie np. gdy dodamy nowy endpoint, dokumentacja automatycznie powinna być zaktualizowana, bez konieczności ręcznej zmiany.


WSKAZÓWKI

Aplikacja korzysta z bazy danych h2. Na potrzeby testów baza danych uruchamiana jest w pamięci. Do właściwego uruchomienia można zainstalować h2 lub także uruchomić ja w pamięci (wtedy należy zmienić adres w application.properties).

Kod można dowolnie edytować - np. zmienić Javowe kolekcje na Vavr/Eclipse Collections, skorzytać z Groovy/Kotlin. Testy także można modyfikować ale nadal muszą testować funkcjonalności w takim samym zakresie/obszarze.

Przygotowana została kolekcja dla programu Postman (SimpleStore.postman_collection.json). Można ja zimportować do klienta Postman - oferuje podstawowe operacje na API (zapis klientów, przedmiotów itp.)

