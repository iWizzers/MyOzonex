package fr.ozonex.myozonex;

public class StructureHttp {
    public enum RequestHTTP {
        Get,
        Update,
        Create
    };

    public static String requestString[] = {
            "get",
            "update",
            "create"
    };

    public enum PageHTTP {
        PageLogin,
        PageData,
        PageEvents,
        PageAutomatisation,
        PageHorlogerie,
        PageBassin,
        PageCapteurs,
        PagePompeFiltration,
        PageFiltre,
        PageSurpresseur,
        PageChauffage,
        PageOzonateur,
        PageLampesUV,
        PageElectrolyseur,
        PageRegulateurPhGlobal,
        PageRegulateurPhPlus,
        PageRegulateurPhMoins,
        PageRegulateurORP,
        PageAlgicide,
        PageEclairage,
        Changes
    };

    public static String pageString[] = {
            "login",
            "data",
            "events",
            "automatisation",
            "horlogerie",
            "bassin",
            "capteurs",
            "pompe_filtration",
            "filtre",
            "surpresseur",
            "chauffage",
            "ozonateur",
            "lampes_uv",
            "electrolyseur",
            "reg_ph",
            "reg_ph_plus",
            "reg_ph_moins",
            "reg_orp",
            "algicide",
            "eclairage",
            "changes"
    };

    private RequestHTTP request;
    private PageHTTP page;
    private String data;
    private boolean fromBt;

    public StructureHttp(RequestHTTP request, PageHTTP page, String data, boolean fromBt) {
        this.request = request;
        this.page = page;
        this.data = data;
        this.fromBt = fromBt;
    }

    public RequestHTTP getRequest() {
        return request;
    }

    public String getRequestString() {
        return requestString[request.ordinal()];
    }

    public PageHTTP getPage() {
        return page;
    }

    public String getPageString() {
        return pageString[page.ordinal()];
    }

    public String getData() {
        return data;
    }

    public boolean isFromBt() {
        return fromBt;
    }
}
