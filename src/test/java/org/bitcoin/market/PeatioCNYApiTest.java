package org.bitcoin.market;

import com.alibaba.fastjson.JSONObject;
import org.bitcoin.market.bean.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

public class PeatioCNYApiTest {

    private AppAccount getAppAccount() {
        AppAccount appAccount = new AppAccount();
        appAccount.setId(1L);
        //appAccount.setAccessKey("xxx"); // todo 替换为access_key
        //appAccount.setSecretKey("yyy"); // todo 替换为secret_key
        return appAccount;
    }

    @Test
    public void testBuyAndCancel() throws Exception {

        Double amount = 0.01;
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.buy(getAppAccount(), amount, 10, new SymbolPair(Symbol.btc, Symbol.usd));
        BitOrder order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.none, order.getStatus());
        assertEquals(amount, order.getOrderAmount());
        assertEquals(0.0, order.getProcessedAmount());
        market.cancel(getAppAccount(), orderId, null);
        order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.cancelled, order.getStatus());
    }

    @Test
    public void testSellAndCancel() throws Exception {

        Double amount = 0.01;
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = market.sell(getAppAccount(), amount, 10000, new SymbolPair(Symbol.btc, Symbol.usd));
        BitOrder order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.none, order.getStatus());
        assertEquals(amount, order.getOrderAmount());
        assertEquals(0.0, order.getProcessedAmount());
        market.cancel(getAppAccount(), orderId, null);
        order = market.getOrder(getAppAccount(), orderId, null);
        assertNotNull(order);
        assertEquals(OrderStatus.cancelled, order.getStatus());
    }

    @Test
    public void testGetInfo() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Asset asset = market.getInfo(getAppAccount());
        assertNotNull(asset);
    }

    @Test
    public void testGetOrder() throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        Long orderId = 434669L;
        BitOrder order = market.getOrder(getAppAccount(), orderId, new SymbolPair(Symbol.btc, Symbol.usd));
        assertNotNull(order);
    }

    @Test
    public void testGetRunningOrder() throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        List<BitOrder> bitOrders = market.getRunningOrders(getAppAccount());
        assertTrue(bitOrders.size() > 0);
    }


    @Test
    public void testGetKlineDate() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        List<Kline> datas = market.getKlineDate(Symbol.btc);
        assertTrue(datas.size() > 0);

    }

    @Test
    public void testGetKline5Min() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        List<Kline> datas = market.getKline5Min(Symbol.btc);
        assertTrue(datas.size() > 0);

    }

    @Test
    public void testGetKline1Min() throws Exception {
        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        List<Kline> datas = market.getKline5Min(Symbol.btc);
        assertTrue(datas.size() > 0);
    }

    @Test
    public void testTicker() throws Exception {

        AbstractMarketApi abstractMarketApi = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        double ticker = abstractMarketApi.ticker(new SymbolPair(Symbol.btc, Symbol.cny));
        assertTrue(ticker > 0.0);

    }

    @Test
    public void testDepth() throws Exception {

        AbstractMarketApi market = MarketApiFactory.getInstance().getMarket(Market.PeatioCNY);
        JSONObject depth = market.get_depth(new SymbolPair(Symbol.btc, Symbol.cny), true);
        Assert.assertTrue(depth.containsKey("asks"));
        Assert.assertTrue(depth.containsKey("bids"));

    }
}
