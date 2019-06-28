import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Admin, Resource, ListGuesser } from 'react-admin';
import jsonServerProvider from 'ra-data-json-server';
import Dashboard from './Dashboard';
import authProvider from './authProvider';
import NotFound from './NotFound';

function App() {
    const dataProvider = jsonServerProvider('http://jsonplaceholder.typicode.com');

    return (
        <Admin  catchAll={NotFound} dashboard={Dashboard} dataProvider={dataProvider} authProvider={authProvider}>
            <Resource name="users" list={ListGuesser} />
        </Admin>
    );
}

export default App;
