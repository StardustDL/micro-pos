import React from 'react';
import logo from './logo.svg';
import { Layout, Button, Space, Typography, PageHeader, Card, Statistic, List, InputNumber, Pagination } from 'antd';
import './App.css';
import { Cart, Product } from './models';

const { Header, Footer, Sider, Content } = Layout;
const { Text, Link } = Typography;

function App() {
  const [products, setProducts] = React.useState<Product[]>([{
    id: "1",
    name: "Product 1",
    price: 1.99,
    description: "This is product 1",
    image: "https://picsum.photos/200"
  }]);
  const [cart, setCart] = React.useState<Cart>({
    items: [{
      id: "1",
      quantity: 1
    }],
    id: "defaultCart",
  });

  return (
    <Layout style={{ height: "100vh" }}>
      <Content>
        <PageHeader title="Micro-Pos" subTitle="A demo POS powered by reactive micro-service.">

        </PageHeader>
        <Layout>
          <Content>
            <Card title="Products">
              {
                products.map(product => (
                  <Card.Grid key={product.id}>
                    <Card cover={<img src={product.image} />} title={product.name}>
                      <Card.Meta style={{ margin: "12px" }} title={<Statistic value={product.price}></Statistic>} description={product.description}></Card.Meta>
                    </Card>
                  </Card.Grid>
                ))
              }
            </Card>
            <Pagination defaultCurrent={1} total={50} style={{ textAlign: "center" }}></Pagination>
          </Content>
          <Sider theme="light" width={400}>
            <Space direction="vertical">
              <List style={{ margin: "10px" }} dataSource={cart.items} renderItem={item =>
                <List.Item>
                  <Space direction="horizontal">
                    <Text>{products.find(x => x.id === item.id)?.name ?? item.id}</Text>
                    <InputNumber min={0} controls={true} defaultValue={item.quantity}></InputNumber>
                  </Space>
                </List.Item>
              }></List>
              <Button>Checkout</Button>
            </Space>
          </Sider>
        </Layout>
      </Content>
    </Layout>
  );
}

export default App;
