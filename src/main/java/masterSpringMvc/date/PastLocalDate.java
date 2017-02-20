package masterSpringMvc.date;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
public @interface PastLocalDate {
	String message() default "{javax.validation.constraints.Past.message}";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {}; 
	
	class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {
		@Override
		public void initialize(PastLocalDate past) {}

		@Override
		public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
			return localDate == null || localDate.isBefore(LocalDate.now());
		}		
	}
	
}
