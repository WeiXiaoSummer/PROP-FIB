package Domain;

public class DomainCtrl {

    private DomainCtrl instance = null;
    private LZ78 lz78;
    private LZSS lzss;
    private JPEG jpeg;
    private GlobalHistory globalHistory;

    private DomainCtrl(){}

    public DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    public void initializeDomainCtrl() {

    }

}
