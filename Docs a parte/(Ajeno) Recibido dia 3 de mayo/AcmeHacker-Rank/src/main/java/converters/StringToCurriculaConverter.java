package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CurriculaRepository;

import domain.Curricula;



@Component
@Transactional
public class StringToCurriculaConverter implements Converter<String,Curricula> {

	@Autowired
	CurriculaRepository repository;
	
	@Override
	public Curricula convert(String s) {
		Curricula res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
