package com.olym.mjt.databean.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.olym.mjt.utils.StringUtils;
import java.io.Serializable;

@DatabaseTable
public class Company
        implements Serializable, Cloneable
{
    private static final long serialVersionUID = -320509115872200611L;
    @DatabaseField
    private String address;
    @DatabaseField
    private int areaId;
    @DatabaseField
    private float balance;
    @DatabaseField
    private int cityId;
    @DatabaseField
    private int countryId;
    @DatabaseField
    private int createTime;
    @DatabaseField
    private String description;
    @DatabaseField(id=true)
    private int id;
    @DatabaseField
    private int industryId;
    @DatabaseField
    private int isAuth;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;
    @DatabaseField
    private String name;
    @DatabaseField
    private int natureId;
    @DatabaseField
    private long payEndTime;
    @DatabaseField
    private int payMode;
    @DatabaseField
    private int provinceId;
    @DatabaseField
    private int scale;
    @DatabaseField
    private int status;
    @DatabaseField
    private float total;
    @DatabaseField
    private String website;

    public Object clone()
            throws CloneNotSupportedException
    {
        return super.clone();
    }

    public boolean equals(Object paramObject)
    {
        int i12 = 1;
        boolean bool1 = true;
        if ((paramObject == null) || (!(paramObject instanceof Company))) {
            bool1 = false;
        }
        while (paramObject == this) {
            return bool1;
        }
        paramObject = (Company)paramObject;
        int i;
        int j;
        label72:
        int k;
        label86:
        int m;
        label100:
        boolean bool2;
        boolean bool3;
        int n;
        label140:
        int i1;
        label154:
        int i2;
        label168:
        int i3;
        label182:
        int i4;
        label196:
        int i5;
        label210:
        int i6;
        label225:
        int i7;
        label240:
        boolean bool4;
        int i8;
        label268:
        int i9;
        label283:
        int i10;
        label297:
        int i11;
        if (this.id == ((Company)paramObject).id)
        {
            i = 1;
            bool1 = StringUtils.strEquals(this.name, ((Company)paramObject).name);
            if (this.industryId != ((Company)paramObject).industryId) {
                break label391;
            }
            j = 1;
            if (this.natureId != ((Company)paramObject).natureId) {
                break label396;
            }
            k = 1;
            if (this.scale != ((Company)paramObject).scale) {
                break label402;
            }
            m = 1;
            bool2 = StringUtils.strEquals(this.description, ((Company)paramObject).description);
            bool3 = StringUtils.strEquals(this.website, ((Company)paramObject).website);
            if (this.countryId != ((Company)paramObject).countryId) {
                break label408;
            }
            n = 1;
            if (this.provinceId != ((Company)paramObject).provinceId) {
                break label414;
            }
            i1 = 1;
            if (this.cityId != ((Company)paramObject).cityId) {
                break label420;
            }
            i2 = 1;
            if (this.areaId != ((Company)paramObject).areaId) {
                break label426;
            }
            i3 = 1;
            if (this.createTime != ((Company)paramObject).createTime) {
                break label432;
            }
            i4 = 1;
            if (this.isAuth != ((Company)paramObject).isAuth) {
                break label438;
            }
            i5 = 1;
            if (this.longitude != ((Company)paramObject).longitude) {
                break label444;
            }
            i6 = 1;
            if (this.latitude != ((Company)paramObject).latitude) {
                break label450;
            }
            i7 = 1;
            bool4 = StringUtils.strEquals(this.address, ((Company)paramObject).address);
            if (this.total != ((Company)paramObject).total) {
                break label456;
            }
            i8 = 1;
            if (this.balance != ((Company)paramObject).balance) {
                break label462;
            }
            i9 = 1;
            if (this.payMode != ((Company)paramObject).payMode) {
                break label468;
            }
            i10 = 1;
            if (this.payEndTime != ((Company)paramObject).payEndTime) {
                break label474;
            }
            i11 = 1;
            label312:
            if (this.status != ((Company)paramObject).status) {
                break label480;
            }
        }
        for (;;)
        {
            return 0x1 & i & bool1 & j & k & m & bool2 & bool3 & n & i1 & i2 & i3 & i4 & i5 & i6 & i7 & bool4 & i8 & i9 & i10 & i11 & i12;
            i = 0;
            break;
            label391:
            j = 0;
            break label72;
            label396:
            k = 0;
            break label86;
            label402:
            m = 0;
            break label100;
            label408:
            n = 0;
            break label140;
            label414:
            i1 = 0;
            break label154;
            label420:
            i2 = 0;
            break label168;
            label426:
            i3 = 0;
            break label182;
            label432:
            i4 = 0;
            break label196;
            label438:
            i5 = 0;
            break label210;
            label444:
            i6 = 0;
            break label225;
            label450:
            i7 = 0;
            break label240;
            label456:
            i8 = 0;
            break label268;
            label462:
            i9 = 0;
            break label283;
            label468:
            i10 = 0;
            break label297;
            label474:
            i11 = 0;
            break label312;
            label480:
            i12 = 0;
        }
    }

    public String getAddress()
    {
        return this.address;
    }

    public int getAreaId()
    {
        return this.areaId;
    }

    public float getBalance()
    {
        return this.balance;
    }

    public int getCityId()
    {
        return this.cityId;
    }

    public int getCountryId()
    {
        return this.countryId;
    }

    public int getCreateTime()
    {
        return this.createTime;
    }

    public String getDescription()
    {
        return this.description;
    }

    public int getId()
    {
        return this.id;
    }

    public int getIndustryId()
    {
        return this.industryId;
    }

    public int getIsAuth()
    {
        return this.isAuth;
    }

    public double getLatitude()
    {
        return this.latitude;
    }

    public double getLongitude()
    {
        return this.longitude;
    }

    public String getName()
    {
        return this.name;
    }

    public int getNatureId()
    {
        return this.natureId;
    }

    public long getPayEndTime()
    {
        return this.payEndTime;
    }

    public int getPayMode()
    {
        return this.payMode;
    }

    public int getProvinceId()
    {
        return this.provinceId;
    }

    public int getScale()
    {
        return this.scale;
    }

    public int getStatus()
    {
        return this.status;
    }

    public float getTotal()
    {
        return this.total;
    }

    public String getWebsite()
    {
        return this.website;
    }

    public void setAddress(String paramString)
    {
        this.address = paramString;
    }

    public void setAreaId(int paramInt)
    {
        this.areaId = paramInt;
    }

    public void setBalance(float paramFloat)
    {
        this.balance = paramFloat;
    }

    public void setCityId(int paramInt)
    {
        this.cityId = paramInt;
    }

    public void setCountryId(int paramInt)
    {
        this.countryId = paramInt;
    }

    public void setCreateTime(int paramInt)
    {
        this.createTime = paramInt;
    }

    public void setDescription(String paramString)
    {
        this.description = paramString;
    }

    public void setId(int paramInt)
    {
        this.id = paramInt;
    }

    public void setIndustryId(int paramInt)
    {
        this.industryId = paramInt;
    }

    public void setIsAuth(int paramInt)
    {
        this.isAuth = paramInt;
    }

    public void setLatitude(double paramDouble)
    {
        this.latitude = paramDouble;
    }

    public void setLongitude(double paramDouble)
    {
        this.longitude = paramDouble;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setNatureId(int paramInt)
    {
        this.natureId = paramInt;
    }

    public void setPayEndTime(long paramLong)
    {
        this.payEndTime = paramLong;
    }

    public void setPayMode(int paramInt)
    {
        this.payMode = paramInt;
    }

    public void setProvinceId(int paramInt)
    {
        this.provinceId = paramInt;
    }

    public void setScale(int paramInt)
    {
        this.scale = paramInt;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }

    public void setTotal(float paramFloat)
    {
        this.total = paramFloat;
    }

    public void setWebsite(String paramString)
    {
        this.website = paramString;
    }
}

