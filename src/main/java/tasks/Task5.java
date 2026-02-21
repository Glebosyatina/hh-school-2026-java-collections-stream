package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Задача 5
Расширим предыдущую задачу.
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 {

  private final PersonConverter personConverter;

  public Task5(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {

    List<ApiPersonDto> result = new ArrayList<>();
    //бежим по персонам, из мапы достаем нужный id региона и потом конвертим в ApiPersonDto
    for (Person pers : persons){
      Integer areaId = personAreaIds.get(pers.id());
      common.ApiPersonDto persDto = personConverter.convert(pers, areaId);
      result.add(persDto);
    }
    return result;
  }
}
