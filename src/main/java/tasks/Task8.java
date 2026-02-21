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

    //для сбора резюме по каждой персоне, заполняем мапу, ключи id персон, заполянем тут кллючи чтобы те у кого нет резюме тоже не потерялись
    Map<Integer, List<Resume>> mapPersons = new HashMap<>();
    for (Person pers : persons){
      mapPersons.put(pers.id(), new ArrayList<>());
    }

    //заполняем по каждому человеку список его резюме
    for (Resume resume : resumes){
      Integer persId = resume.personId();
      //вытаскиваем и дополняем список резюме каждой персоны
      List<Resume> persResumes = mapPersons.get(persId);

      persResumes.add(resume);

      mapPersons.put(persId, persResumes);
    }

    Set<PersonWithResumes> result = new HashSet<>();

    //теперь из этой мапы переносим в сет из PersonWithResumes
    for (Map.Entry<Integer, List<Resume>> entry : mapPersons.entrySet()){
      Person pers = mapIdPerson.get(entry.getKey());
      Set<Resume> resms = new HashSet<>(entry.getValue());
      //создаем PersonWithResume инициализуем поля Person через мапу, а поле Set<Resumes> через конверт List -> Set
      result.add(new PersonWithResumes(pers, resms));
    }

    return result;
  }
}
