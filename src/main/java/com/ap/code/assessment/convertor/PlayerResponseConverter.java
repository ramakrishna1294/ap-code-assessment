package com.ap.code.assessment.convertor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ap.code.assessment.controller.response.PlayerResponse;
import com.ap.code.assessment.entity.Player;

@Component
public class PlayerResponseConverter implements Converter<Player, PlayerResponse>{

	@Override
	public PlayerResponse convert(Player player) {
		
		List<String> sports = Objects.isNull(player.getRelation())? null:
							player.getRelation().stream().map(relation -> relation.getSport().getName()).collect(Collectors.toList());
		
		return PlayerResponse.builder()
					  .age(player.getAge())
					  .email(player.getEmail())
					  .level(player.getLevel())
					  .gender(player.getGender())
					  .sports(sports)
					  .build();
	}

}
