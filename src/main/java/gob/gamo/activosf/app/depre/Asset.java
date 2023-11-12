package gob.gamo.activosf.app.depre;

import java.util.Date;
import java.util.List;

public class Asset {
    public String Name;
    public Date PurchaseDate;
    public double PurchasePrice;
    public double ResidualValue;
    public double Section179;
    public int UsefulLife;
    private TAX_Lifes _TaxLife;
    private int TaxLife;
    public List<DepYear> GaapDepreciation;

    public List<DepYear> TaxDepreciation;
    // Empty Constructor
    public Asset() {
        Name = "";
        PurchaseDate = new Date();
        PurchasePrice = 0;
        ResidualValue = 0;
        UsefulLife = 0;
        TaxLife = TAX_Lifes.NONE.getValor();
        Section179 = 0;
        return;
    }

    // Full Constructor
    public Asset(
            String sName,
            Date dtPurchaseDate,
            double fPurchasePrice,
            double fResidualValue,
            int iUSefulLife,
            int iTaxLife,
            double fSection179,
            String sDescription) {
        Name = sName;
        // Description = sDescription;
        PurchaseDate = dtPurchaseDate;
        PurchasePrice = fPurchasePrice;
        ResidualValue = fResidualValue;
        UsefulLife = iUSefulLife;
        Section179 = fSection179;
        TaxLife = iTaxLife;
        setTaxLife(TaxLife);
    }

    // Limited Constructor
    public Asset(double fPurchasePrice, double fResidualValue, int iUSefulLife, String sName, double fSection179) {
        Name = sName;
        // Description = "";
        PurchaseDate = new Date();
        PurchasePrice = fPurchasePrice;
        ResidualValue = fResidualValue;
        UsefulLife = iUSefulLife;
        Section179 = fSection179;
        TaxLife = TAX_Lifes.NONE.getValor();
        setTaxLife(TaxLife);
    }

    public int getTaxLife() {
        return (int) this._TaxLife.getValor();
    }

    public void setTaxLife(int value) {
        if (value == 20) {
            _TaxLife = TAX_Lifes.TWENTY;
        } else if (value == 15) {
            _TaxLife = TAX_Lifes.FIFTEEN;
        } else if (value == 10) {
            _TaxLife = TAX_Lifes.TEN;
        } else if (value == 7) {
            _TaxLife = TAX_Lifes.SEVEN;
        } else if (value == 5) {
            _TaxLife = TAX_Lifes.FIVE;
        } else if (value == 3) {
            _TaxLife = TAX_Lifes.THREE;
        } else if (value == 0) {
            _TaxLife = TAX_Lifes.NONE;
        } else {
            throw new RuntimeException("INVALID_TAX_YEAR");
        }
    }

    // SL Calc
    public void calcSL() {
        GaapDepreciation = DepCalcs.CalcSL(this);
    }

    public void calcDB200() {
        GaapDepreciation = DepCalcs.CalcDB200(this);
    }

    public void calcDB150() {
        GaapDepreciation = DepCalcs.CalcDB150(this);
    }

    public void calcSYD() {
        GaapDepreciation = DepCalcs.CalcSYD(this);
    }

    public void calcMacrsHY() {
        TaxDepreciation = DepCalcs.CalcMacrsHY(this);
    }

    public void calcMacrsMQ() {
        TaxDepreciation = DepCalcs.CalcMacrsMQ(this);
    }
}
