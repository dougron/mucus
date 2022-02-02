package main.java.com.dougron.mucus.mu_framework.mu_controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

@Builder
@ToString
public class MuController {

	@Setter @Getter private MuTag partNameTag;
	@Setter @Getter private MuTag controllerTag;
	@Setter @Getter private String lomPath;
	@Setter @Getter private String controllerName;
	@Setter @Getter private String parameterName;
	
	@Setter @Getter @Builder.Default	private double min = 0.0;
	@Setter @Getter @Builder.Default	private double max = 1.0;
	
	
}
