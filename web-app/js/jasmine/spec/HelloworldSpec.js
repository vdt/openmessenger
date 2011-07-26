/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


describe("Hello Function", function() {
    it("should be able to dispay a message", function() {
        expect("Hello Dude").toEqual(hello("Dude"));
        
        expect("Hello Nat").toEqual(hello("Nat"));
    });
})