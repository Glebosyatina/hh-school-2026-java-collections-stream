package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    //думаю можно без этой проверки обойтись, стрим нормально обрабатывает ситуацию пустого списка
    //тут можно просто использовать skip для стрима, не удаляя первый элемент, думаю лучше так
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // return getNames(persons).stream().distinct().collect(Collectors.toSet());
    //думаю тут можно обойтись без стримов и просто создать сет на основе листа
    return new HashSet<String>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return String.join(" ", person.firstName() != null ? person.firstName() : null,
        person.middleName() != null ? person.middleName() : null,
        person.secondName() != null ? person.secondName() : null);
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    //можно сделать через стрим, компактнее, читабельнее, может упасть если в коллекции попадется несколько дубликатов
    //вылетит IllegalStatException, для этого убираем дубликаты через toMap,с указанием что делать с дубликатами
    return persons.stream()
        .collect(Collectors.toMap(Person::id,
            Person::firstName,
            (exist, replace) -> exist)
        );
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    //как вариант вместо двойного цикла, перекинуть в мапу( 1 проход закинули первый лист в мапу,
    // 2ым проходом пробежались по второй коллекции и если встретили хоть один ключ который уже был то true
    //и в итоге сложность будет не O(N^2), O(2N) = O(N)

    Set<Person> set = new HashSet<>(persons1);
    return persons2.stream()
        .anyMatch(set::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    //count = 0;
    //здесь вместо ручного инкремента счетика проще вернуть count из стрима
    return numbers.filter(num -> num % 2 == 0).count();//forEach(num -> count++);
    //return count;
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    //создали уже отсортированную последовательность
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    //тут создали ее копию (разные объекты, разные ссылки -> изменения на старом объекте никак не влияют на новый
    //в snapshot лежит отсортированная последовательность
    List<Integer> snapshot = new ArrayList<>(integers);
    //тут перемешали первую последовательность
    Collections.shuffle(integers);
    // но когда мы на основе перемешанной последовательности создаем HashSet

    //HashSet внутри использует HashMap, и при добавлениии элементов метод hashCode для Integer возвращает само число,
    // видимо когда у нас диапазон от 1 до N при вставке в массив бакетов,
    // и выполнении внутренней функции HashMap для вычисления индекса бакета они
    // заполняются друг за другом и
    // из за этого и получается такой эффект
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
