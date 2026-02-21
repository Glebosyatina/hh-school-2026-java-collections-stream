package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    //пробежатся по сету перекинуть в мапу где ключом будет id, -> к любому Person будем иметь доступ за O(1)
    Map<Integer, Person> personsMap = new HashMap<>();
    for (Person pers : persons){
      personsMap.put(pers.id(), pers);
    }

    //собираем в нужном порядке в List, по итогу думаю сложность O(m+n)
    List<Person> result = new ArrayList<>();
    for (Integer id : personIds){
      result.add(personsMap.get(id));
    }

    return result;
  }
}
