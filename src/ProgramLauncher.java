import Taxes.EmployeeTaxes;
import Taxes.EmployerTaxes;

public class ProgramLauncher {

    public void launch() {

        UI ui = new UI();
        int salaryNet = ui.inputSalaryNet();

        EmployerTaxes employer = new EmployerTaxes(salaryNet);
        EmployeeTaxes employee = new EmployeeTaxes(salaryNet);

        System.out.println(employee.printSalariesAndTotal());
        System.out.println(employee.printListTaxes());
        System.out.println(employer.printListTaxes());
    }
}
