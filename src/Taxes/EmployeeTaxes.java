package Taxes;

public class EmployeeTaxes extends Taxes {

    public EmployeeTaxes(float salaryNet) {
        super(salaryNet, 6.58F, 0.4F, 3.2F, 1.4F, 2.2F);
    }

    public float totalTaxesBGNwithoutTTI() {
        return ((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                gIMPercentage + aCPIPercentage) / 100) * insuranceAmount;
    }

    @Override
    public float totalTaxesBGN() {
        return (((pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                gIMPercentage + aCPIPercentage) / 100) * insuranceAmount) + tTIBGN;
    }

    public float totalTaxesPercentageWithoutTTI() {
        return pensionFundPercentage + unemploymentFundPercentage + healthInsurancePercentage +
                gIMPercentage + aCPIPercentage;
    }

    public String printSalariesAndTotal() {
        return String.format(
                "%-66s%12.2f лв.%n" +
                        "%-66s%12.2f лв.%n" +
                        "%-66s%12.2f лв.",
                "Нетна заплата: ", salaryNet,
                "Брутна заплата: ", salaryNet + totalTaxesBGN(),
                "Общо разходи работодател: ", salaryNet + totalTaxesBGN() + EmployerTaxes.totalTaxesBGN);
    }

    @Override
    public String printListTaxes() {
        return String.format(
                "%n%s%n" +
                        "%n%-60s%.2f%2%%12.2f лв." +
                        "%n%-60s%.2f%2%%12.2f лв." +
                        "%n%-60s%.2f%2%%12.2f лв." +
                        "%n%-60s%.2f%2%%12.2f лв." +
                        "%n%-60s%.2f%2%%12.2f лв." +
                        "%n%-59s%.2f%2%%12.2f лв." +
                        "%n%-59s%.2f%2%%12.2f лв." +
                        "%n%-59s%19.2f лв.",
                "Удръжки Служител (Върху Осигурителен Доход)",
                "Фонд Пенсии", pensionFundPercentage, percentageToBGN(pensionFundPercentage),
                "Фонд Безработица", unemploymentFundPercentage, percentageToBGN(unemploymentFundPercentage),
                "Здравно Осигуряване", healthInsurancePercentage, percentageToBGN(healthInsurancePercentage),
                "Общо Заболяване и Майчинство", gIMPercentage, percentageToBGN(gIMPercentage),
                "Допълнително Задължително Пенсионно Осигуряване", aCPIPercentage, percentageToBGN(aCPIPercentage),
                "Общо осигурителни вноски", totalTaxesPercentageWithoutTTI(), totalTaxesBGNwithoutTTI(),
                "Данък върху Общ Доход", tTIPercent * 100F, tTIBGN,
                "Общо удръжки", totalTaxesBGN());
    }
}