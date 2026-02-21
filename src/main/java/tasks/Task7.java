package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {

    //пробегаемся по вакансиям компанией и закидываем в мапу
    // применительно к стримам: из стрима команий вытягиваем стрим сетов вакасий, из него вытягиваем стрим названий и собираем их в мапу
    Set<String> result = companies.stream()
        .map(Company::getVacancies)
        .flatMap(Set::stream)
        .map(Vacancy::getTitle)
        .collect(Collectors.toSet());

    return result;
  }

}
