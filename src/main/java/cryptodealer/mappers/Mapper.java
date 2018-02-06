package cryptodealer.mappers;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T, R> {

	R map(T input);

	default List<R> mapAll(List<T> inputs) {

		return inputs.stream().map(this::map).collect(Collectors.toList());
	}
}
