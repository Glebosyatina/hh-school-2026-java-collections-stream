package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Set<String> result = new HashSet<>();

    Map<Integer, String> areasMap = new HashMap<>(); //конвертим в map чтобы брать по индексу название
    for (Area area : areas){
      areasMap.put(area.getId(), area.getName());
    }

    //надо пробежаться по персонам, вытащить id регионов связанных с этим персом, и склеить строки с именем перса и регионами, связанными с ним
    //collectors.joining для склейки строк
    for (Person pers : persons){
      Set<Integer> regionsIds = personAreaIds.get(pers.id());
      for (Integer regId : regionsIds){
        //склеивам имя - регион
        String persReg = Stream.of(pers.firstName(), areasMap.get(regId))
            .collect(Collectors.joining(" - ","", ""));
        //добавляем в релуьтирующий set
        result.add(persReg);
      }
    }

    return result;
  }
}
