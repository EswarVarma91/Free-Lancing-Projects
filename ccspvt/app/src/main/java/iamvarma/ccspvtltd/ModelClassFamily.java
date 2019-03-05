package iamvarma.ccspvtltd;

class ModelClassFamily {

    String name;
    String aadhar;

    public ModelClassFamily(String name, String aadhar) {
        this.name = name;
        this.aadhar = aadhar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }
}
