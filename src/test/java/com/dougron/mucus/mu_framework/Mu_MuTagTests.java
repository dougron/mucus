package test.java.com.dougron.mucus.mu_framework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

class Mu_MuTagTests
{

	@Test
	final void when_mutag_is_added_then_hasTag_is_true ()
	{
		Mu mu = new Mu("mutag_test");
		mu.addTag(MuTag.ACCENTED);
		assertThat(mu.hasTag(MuTag.ACCENTED)).isTrue();
	}
	
	
	@Test
	void when_mutags_are_added_and_one_is_removed_then_hasTag_for_that_one_is_false_but_not_others () throws Exception
	{
		Mu mu = new Mu("mutag_test");
		mu.addTag(MuTag.ACCENTED);
		mu.addTag(MuTag.PART_1);
		assertThat(mu.hasTag(MuTag.ACCENTED)).isTrue();
		assertThat(mu.hasTag(MuTag.PART_1)).isTrue();
		mu.removeMuTagBundleContainingTag(MuTag.ACCENTED);
		assertThat(mu.hasTag(MuTag.ACCENTED)).isFalse();
		assertThat(mu.hasTag(MuTag.PART_1)).isTrue();
	}

}
