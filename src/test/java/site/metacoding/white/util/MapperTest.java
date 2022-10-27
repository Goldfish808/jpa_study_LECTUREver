package site.metacoding.white.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
class Dog {
    private Integer id;
    private String name;
}

@NoArgsConstructor
@Setter
@Getter
class DogDto {
    private Integer id;
    private String name;

    public DogDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DogDto [id=" + id + ", name=" + name + "]";
    }

}

public class MapperTest {

    @Test
    public void convert_test() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog(1, "강아지"));
        dogList.add(new Dog(2, "고양이"));

        // List<Dog> -> List<DogDto>
        List<DogDto> dogDtoList = new ArrayList<>();
        for (Dog dog : dogList) {
            DogDto dogDto = new DogDto();
            dogDto.setId(dog.getId());
            dogDto.setName(dog.getName());
            dogDtoList.add(dogDto);
        }

        System.out.println(dogDtoList);
    }

    @Test
    public void convert_test2() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog(1, "강아지"));
        dogList.add(new Dog(2, "고양이"));

        // List<Dog> -> List<DogDto>
        List<DogDto> dogDtoList = dogList.stream()
                .map((dog) -> new DogDto(dog.getId(), dog.getName()))
                .collect(Collectors.toList());

        System.out.println(dogDtoList);
    }
}