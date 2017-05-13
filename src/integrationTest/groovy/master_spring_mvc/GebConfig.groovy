package master_spring_mvc
import org.openqa.selenium.Dimension
import org.openqa.selenium.opera.OperaDriver

reportsDir = new File('./build/geb-reports')
driver = {
	def driver = new OperaDriver()
	driver.manage().window().setSize(new Dimension(1024, 768))
	return driver
}