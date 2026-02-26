package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {

    //findResumes принимаем коллекцию idшек персон, нужно из коллекции персон вытащить idшки, и затем передать эту коллекцию
    Map<Integer, Person> mapIdPerson = persons.stream()
        .collect(Collectors.toMap(Person::id, x -> x));

    Set<Resume> resumes = personService.findResumes(mapIdPerson.keySet());

    //мапа хранящая id персоны и набор принадлежащих ей резюме
    Map<Integer, Set<Resume>> mapPers = resumes.stream()
        .collect(Collectors.groupingBy(Resume::personId, Collectors.toSet()));

    //сначала достаем все id персон( чтобы не пропустить тех у кого нет резюме), далее через
    //.map создаем PersonWithResume(не забыв проверить если резюме нет(null) то создать пустой сет)
    return mapIdPerson.keySet().stream()
        .map(p -> {
          return new PersonWithResumes(mapIdPerson.get(p), mapPers.get(p) == null ? Collections.emptySet() : mapPers.get(p));
        })
        .collect(Collectors.toSet());
  }
}
